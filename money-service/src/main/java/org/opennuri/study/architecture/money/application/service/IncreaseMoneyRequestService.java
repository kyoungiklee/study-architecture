package org.opennuri.study.architecture.money.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.common.CountDownLatchManager;
import org.opennuri.study.architecture.common.UseCase;
import org.opennuri.study.architecture.common.task.RechargingMoneyTask;
import org.opennuri.study.architecture.common.task.SubTask;
import org.opennuri.study.architecture.money.application.port.in.IncreaseMoneyRequestCommand;
import org.opennuri.study.architecture.money.application.port.in.IncreaseMoneyRequestUseCase;
import org.opennuri.study.architecture.money.application.port.out.ChangeStatusPort;
import org.opennuri.study.architecture.money.application.port.out.IncreaseMoneyPort;
import org.opennuri.study.architecture.money.application.port.out.kafka.SendRechargingMoneyTaskPort;
import org.opennuri.study.architecture.money.application.port.out.membership.MembershipServicePort;
import org.opennuri.study.architecture.money.application.port.out.membership.MembershipStatus;
import org.opennuri.study.architecture.money.domain.MoneyChangingRequest;
import org.opennuri.study.architecture.money.domain.ChangingMoneyRequestStatus;
import org.opennuri.study.architecture.money.domain.ChangingMoneyRequestType;
import org.opennuri.study.architecture.money.domain.MemberMoney;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase {

    private final CountDownLatchManager countDownLatchManager;

    private final IncreaseMoneyPort increaseMoneyPort;
    private final ChangeStatusPort changeStatusPort;
    private final MembershipServicePort membershipServicePort;
    private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;
    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {
        //1. 고객 정보가 정상인지 확인 (회원)
        if (command.getMembershipId() == null) {
            log.info("membershipId is null");
            throw new IllegalArgumentException("membershipId is null");
        }
        MembershipStatus status = membershipServicePort.getMembershipInfo(command.getMembershipId());
        log.info("membership status: {}", status);
        if (status.getMembershipStatusType() != MembershipStatus.MembershipStatusType.NORMAL) {
            log.info("membership is not normal");
            throw new IllegalArgumentException("membership is not normal");
        }

        //2. 고객에게 연동된 계좌가 있는 지 확인, 연동된 계좌에 잔액이 충분한지 확인 (뱅킹))
        //3. 법인계좌의 상태가 정상인지 확인 (뱅킹)
        //4. 증액을 위한 충전 요청생성 및 기록 (머니)
        MoneyChangingRequest changeMoneyRequest = increaseMoneyPort.createChangeMoneyRequest(
                new MoneyChangingRequest.MembershipId(Long.parseLong(command.getMembershipId())),
                new MoneyChangingRequest.RequestType(ChangingMoneyRequestType.DEPOSIT),
                new MoneyChangingRequest.MoneyAmount(command.getMoneyAmount()),
                new MoneyChangingRequest.RequestStatus(ChangingMoneyRequestStatus.REQUESTED),
                new MoneyChangingRequest.RequestDateTime(LocalDateTime.now()),
                new MoneyChangingRequest.UUID(UUID.randomUUID().toString())
        );
        //5. 펌뱅킹을 수행 (개인계좌 --> 법인계좌 이체) (뱅킹)

        //6. 성공시 증액 요청 상태를 완료로 변경(머니)
        MoneyChangingRequest changeStatus =
                changeStatusPort.changeRequestStatus(changeMoneyRequest.getUuid(), ChangingMoneyRequestStatus.SUCCESS);

        //7-1. 멤버머니 잔액을 증액(머니)
        MemberMoney memberMoney = increaseMoneyPort.increaseMoney(
                new MemberMoney.MembershipId(changeMoneyRequest.getMembershipId())
                , new MemberMoney.MoneyAmount(changeMoneyRequest.getMoneyAmount()));

        //7-2. 결과 실패시 실패로 증액요청 상태 변경 후 리턴

        return changeStatus;
    }

    @Override
    public MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command) {

        //Step 1. 요청내용을 기록한다.
        MoneyChangingRequest changeMoneyRequest = increaseMoneyPort.createChangeMoneyRequest(
                new MoneyChangingRequest.MembershipId(Long.parseLong(command.getMembershipId())),
                new MoneyChangingRequest.RequestType(ChangingMoneyRequestType.DEPOSIT),
                new MoneyChangingRequest.MoneyAmount(command.getMoneyAmount()),
                new MoneyChangingRequest.RequestStatus(ChangingMoneyRequestStatus.REQUESTED),
                new MoneyChangingRequest.RequestDateTime(LocalDateTime.now()),
                new MoneyChangingRequest.UUID(UUID.randomUUID().toString())
        );
        //Step 2. 고객에게 연동된 계좌가 있는 지 확인, 연동된 계좌에 잔액이 충분한지 확인 (뱅킹))
        //Step 3. 법인계좌의 상태가 정상인지 확인 (뱅킹)
        //Step 4. 증액을 위한 충전 요청생성 및 기록 (머니)
        //Step 5. 펌뱅킹을 수행 (개인계좌 --> 법인계좌 이체) (뱅킹)'
        //Step 6. 성공시 증액 요청 상태를 완료로 변경(머니)
        //Step 7-1. 멤버머니 잔액을 증액(머니)
        //Step 7-2. 결과 실패시 실패로 증액요청 상태 변경 후 리턴

        //SubTask, Task 생성

        SubTask validMemberTask = SubTask.builder()
                .membershipId(command.getMembershipId())
                .subTaskName("고객정보 유효성 체크")
                .subTaskType(SubTask.SubTaskType.MEMBERSHIP)
                .subTaskStatus(SubTask.SubTaskStatus.STARTED)
                .build();


        SubTask validBankingTask = SubTask.builder()
                .membershipId(command.getMembershipId())
                .subTaskName("뱅크계좌 유효성 체크")
                .subTaskType(SubTask.SubTaskType.BANKING)
                .subTaskStatus(SubTask.SubTaskStatus.STARTED)
                .build();

        List<SubTask> subTasks = new ArrayList<>();
        subTasks.add(validMemberTask);
        subTasks.add(validBankingTask);

        log.info("subTasks: {}", subTasks);


        RechargingMoneyTask rechargingMoneyTask = RechargingMoneyTask.builder()
                .taskId(UUID.randomUUID().toString())
                .taskName("머니 증액 작업")
                .membershipId(command.getMembershipId())
                .moneyAmount(command.getMoneyAmount())
                .subTasks(subTasks)
                .toBankName("신한은행")
                .toBankAccountNumber("110-1234-1234")
                .build();

        log.info("rechargingMoneyTask: {}", rechargingMoneyTask);
        //Task를 큐에 저장(kafka)
        sendRechargingMoneyTaskPort.sendRechargingMoneyTask(rechargingMoneyTask);
        countDownLatchManager.addCountDownLatch(rechargingMoneyTask.getTaskId(), 1);


        //완료될때까지 기다린다.
        try {
            countDownLatchManager.getCountDownLatch(rechargingMoneyTask.getTaskId()).await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // TaskResult를 가져온다.
        String result = countDownLatchManager.getDataForKey(rechargingMoneyTask.getTaskId());

        MoneyChangingRequest changeStatus = null;
        if(result.equals("success")) {
            changeStatus =
                    changeStatusPort.changeRequestStatus(changeMoneyRequest.getUuid(), ChangingMoneyRequestStatus.SUCCESS);

            MemberMoney memberMoney = increaseMoneyPort.increaseMoney(
                    new MemberMoney.MembershipId(changeMoneyRequest.getMembershipId())
                    , new MemberMoney.MoneyAmount(changeMoneyRequest.getMoneyAmount()));
        } else {
            changeStatus =
                    changeStatusPort.changeRequestStatus(changeMoneyRequest.getUuid(), ChangingMoneyRequestStatus.FAILED);
        }

        //7-2. 결과 실패시 실패로 증액요청 상태 변경 후 리턴

        return changeStatus;
    }
}
