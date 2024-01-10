package org.opennuri.study.architecture.money.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.jetbrains.annotations.NotNull;
import org.opennuri.study.architecture.common.CountDownLatchManager;
import org.opennuri.study.architecture.common.UseCase;
import org.opennuri.study.architecture.common.exception.BusinessException;
import org.opennuri.study.architecture.common.task.RechargingMoneyTask;
import org.opennuri.study.architecture.common.task.SubTask;
import org.opennuri.study.architecture.money.adapter.axon.command.RechargingMoneyRequestCreateCommand;
import org.opennuri.study.architecture.money.application.port.in.RechargingMoneyRequestCommand;
import org.opennuri.study.architecture.money.application.port.in.RechargingMoneyRequestUseCase;
import org.opennuri.study.architecture.money.application.port.out.RechargingMoneyPort;
import org.opennuri.study.architecture.money.application.port.out.kafka.SendRechargingMoneyTaskPort;
import org.opennuri.study.architecture.money.domain.ChangingMoneyRequestStatus;
import org.opennuri.study.architecture.money.domain.ChangingMoneyRequestType;
import org.opennuri.study.architecture.money.domain.MemberMoney;
import org.opennuri.study.architecture.money.domain.MoneyChangingRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@UseCase
@RequiredArgsConstructor

public class RechargingMoneyRequestService implements RechargingMoneyRequestUseCase {
    private final RechargingMoneyPort rechargingMoneyPort;
    private final CountDownLatchManager countDownLatchManager;
    private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;
    private final CommandGateway commandGateway;
    private final FindMemberMoneyService findMemberMoneyService;

    @Override
    public MemberMoney rechargingMoney(RechargingMoneyRequestCommand command) {

        // 고객 요청 UUID 생성(moneyChangingRequestId는 요청이 생성 될 때 자동 생성되므로,
        // 해당 건으로 연결된 요청내용으로 확인 할 수 있도록 UUID를 생성한다.)
        String uuid = UUID.randomUUID().toString();

        //1. 요청정보를 요청상태로 저장한다. (머니)
        saveChangingMoneyStatus(command
                , ChangingMoneyRequestStatus.REQUESTED, uuid);

        //2. 고객의 상태가 정상인지, 연동계좌가 정상인지 확인한다. (회원, 뱅킹)
        String result = checkMembershipAccount(command, uuid);

        // 고객상태확인 및 연결계좌상태 확인이 성공적하면 고객의 Money 잔액을 증액하는 작업을 진행한다.
        MemberMoney memberMoney;
        if (result.equals("SUCCESS")) {

            //4. 고객의 Money 잔액을 증액한다. (머니)
            memberMoney = rechargingMoneyPort.rechargingMoney(
                    new MemberMoney.MembershipId(command.getMembershipId())
                    , command.getMoneyAmount());

            //5. 요청정보를 처리완료 상태로 저장한다. (머니)
            saveChangingMoneyStatus(command
                    , ChangingMoneyRequestStatus.SUCCESS, uuid);

            return memberMoney;
        } else {
            //6. 요청정보를 처리실패 상태로 저장한다. (머니)
            saveChangingMoneyStatus(command
                    , ChangingMoneyRequestStatus.FAILED, uuid);
            throw new BusinessException("머니요청 처리에 실패하였습니다.");
        }
    }


    private void saveChangingMoneyStatus(RechargingMoneyRequestCommand command
            , ChangingMoneyRequestStatus status, String uuid) {

        MoneyChangingRequest changeMoneyRequest = rechargingMoneyPort.createChangeMoneyRequest(
                new MoneyChangingRequest.MembershipId(command.getMembershipId())
                , new MoneyChangingRequest.RequestType(ChangingMoneyRequestType.WITHDRAW)
                , new MoneyChangingRequest.MoneyAmount(command.getMoneyAmount())
                , new MoneyChangingRequest.RequestStatus(status)
                , new MoneyChangingRequest.RequestDateTime(LocalDateTime.now())
                , new MoneyChangingRequest.UUID(uuid));

        if (changeMoneyRequest == null) {
            throw new BusinessException("머니요청 정보 저장에 실패하였습니다.");
        }
    }

    @NotNull
    private String checkMembershipAccount(RechargingMoneyRequestCommand command, String uuid) {
        //1. 고객의 상태가 정상인지 확인용 subTask 생성. (회원)
        //taskName을 properties로 관리할 수 있도록 변경
        SubTask membershipCheckTask = SubTask.builder()
                .membershipId(command.getMembershipId().toString())
                .subTaskName("고객정보 유효성 체크")
                .subTaskType(SubTask.SubTaskType.MEMBERSHIP)
                .subTaskStatus(SubTask.SubTaskStatus.STARTED)
                .build();
        //2. 고객에게 연동된 계좌 정상 여부 확인용 subTask 생성 (뱅킹)
        SubTask accountCheckTask = SubTask.builder()
                .membershipId(command.getMembershipId().toString())
                .subTaskName("연결계좌 유효성 체크")
                .subTaskType(SubTask.SubTaskType.BANKING)
                .subTaskStatus(SubTask.SubTaskStatus.STARTED)
                .build();

        List<SubTask> subTasks = List.of(membershipCheckTask, accountCheckTask);

        //3.kafka로 produce 요청 task 생성(고객 확인 / 연동계좌 확인)
        RechargingMoneyTask rechargingMoneyTask = RechargingMoneyTask.builder()
                .taskId(uuid)
                .taskName("머니 감액 작업")
                .membershipId(command.getMembershipId().toString())
                .moneyAmount(command.getMoneyAmount())
                .subTasks(subTasks)
                .build();

        log.info("rechargingMoneyTask: {}", rechargingMoneyTask);
        //4. kafka로 전송한다.
        sendRechargingMoneyTaskPort.sendRechargingMoneyTask(rechargingMoneyTask);
        //5. CountDownLatch 생성한다. (TaskId를 key로 하여 CountDownLatch를 생성한다.)
        countDownLatchManager.addCountDownLatch(rechargingMoneyTask.getTaskId(), 1);
        log.info("countDownLatch taskId: {}", countDownLatchManager.getCountDownLatch(rechargingMoneyTask.getTaskId()));

        //6. 완료될때까지 기다린다.(kafka TaskConsumer에서 요청내용을 처리후 task.result.topic으로 결과를 produce한다.
        boolean await = countDownLatchManager.await(rechargingMoneyTask.getTaskId());
        //7. 1초 이내에 완료되면 true를 리턴한다. 1초내에 완료되지 않으면 false를 리턴한다.
        if (!await) {
            log.info("timeout");
            throw new RuntimeException("timeout");
        }

        //7. TaskConsumer에서 produce한 처리결과를 ResultConsumer consume하여 countDownLatchManager에 결과를 저장한다. (countDownLatchManager에 결과가 저장되면 countDown을 실행한다.)
        return countDownLatchManager.getResult(rechargingMoneyTask.getTaskId()).orElse("FAIL");
    }


    /**
     * Saga를 이용한 머니 증액 요청, 고객의 머니정보를 조회하여
     *
     * @param command 머니 증액 요청 정보
     * @return MemberMoney 고객의 머니 정보
     */
    @Override
    public MemberMoney rechargingMoneySaga(RechargingMoneyRequestCommand command) {
        //1. 고객의 머니 정보를 조회하여 aggregateId를 가져온다.
        MemberMoney memberMoney = findMemberMoneyService.findMemberMoney(command.getMembershipId());
        String memberMoneyAggregateId = memberMoney.getAggregateId();

        //2. 고객 충전 saga의 시작을 알리는 command를 전송한다.(RechargingMoneyRequestCreateCommand)
        CompletableFuture<Object> objectCompletableFuture
                = commandGateway.send(RechargingMoneyRequestCreateCommand.builder()
                        .aggregateId(memberMoneyAggregateId) //MoneyRechargeSaga의 aggregateId
                        .rechargingRequestAssociationId(UUID.randomUUID().toString()) //command와 연결되는 이벤트의 associationId
                        .membershipId(command.getMembershipId())
                        .amount(command.getMoneyAmount())
                        .build())
                .whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        log.error("rechargingMoneySaga error", throwable);
                        throw new BusinessException("rechargingMoneySaga error");
                    }
                }).thenApply((result) -> {
                    log.info("rechargingMoneySaga result: {}", result);
                    return result;
                });
        Object join = objectCompletableFuture.join();
        log.info("join: {}", join.toString());
        return null;
    }
}
