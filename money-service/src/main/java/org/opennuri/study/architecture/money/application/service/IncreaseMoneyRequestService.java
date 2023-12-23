package org.opennuri.study.architecture.money.application.service;

import jakarta.transaction.Transactional;
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@UseCase
@RequiredArgsConstructor
@Transactional
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase {

    private final CountDownLatchManager countDownLatchManager;

    private final IncreaseMoneyPort increaseMoneyPort;
    private final ChangeStatusPort changeStatusPort;
    private final MembershipServicePort membershipServicePort;
    private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;

    /**
     * 증액요청 처리
     * @param command 증액요청
     * @return 증액요청결과
     */
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
        //Async Task 처리(Taks, SubTask 생성
        //Step 2. 증액 요청 고객의 상태가 정상인지 확인을 위한 태스크 (회원)
        SubTask validMemberTask = SubTask.builder()
                .membershipId(command.getMembershipId())
                .subTaskName("고객정보 유효성 체크")
                .subTaskType(SubTask.SubTaskType.MEMBERSHIP)
                .subTaskStatus(SubTask.SubTaskStatus.STARTED)
                .build();

        //Step 3. 고객연결계좌 상태가 정상인지 확인을 위한 태스크 (뱅킹)
        SubTask validBankingTask = SubTask.builder()
                .membershipId(command.getMembershipId())
                .subTaskName("연결계좌 유효성 체크")
                .subTaskType(SubTask.SubTaskType.BANKING)
                .subTaskStatus(SubTask.SubTaskStatus.STARTED)
                .build();

        List<SubTask> subTasks = new ArrayList<>();
        subTasks.add(validMemberTask);
        subTasks.add(validBankingTask);

        log.info("subTasks: {}", subTasks);

        // Task 생성
        RechargingMoneyTask rechargingMoneyTask = RechargingMoneyTask.builder()
                .taskId(UUID.randomUUID().toString())
                .taskName("머니 증액 작업")
                .membershipId(command.getMembershipId())
                .moneyAmount(command.getMoneyAmount())
                .subTasks(subTasks)
                .build();

        log.info("rechargingMoneyTask: {}", rechargingMoneyTask);
        // kafka로 전송한다.
        sendRechargingMoneyTaskPort.sendRechargingMoneyTask(rechargingMoneyTask);
        // CountDownLatch를 생성한다. (TaskId를 key로 하여 CountDownLatch를 생성한다.)
        countDownLatchManager.addCountDownLatch(rechargingMoneyTask.getTaskId(), 1);

        //완료될때까지 기다린다.
        boolean await = false;
        try {
            // kafka TaskConsumer에서 요청내용을 처리후 task.result.topic으로 결과를 produce한다.
            // money-service에서는 task.result.topic을 subscribe하여 결과를 받아 처리후CountDownLatch를 countDown한다
            // 1초 이내에 완료되면 true를 리턴한다. 1초내에 완료되지 않으면 false를 리턴한다.
            await = countDownLatchManager.getCountDownLatch(rechargingMoneyTask.getTaskId()).await(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 1초 이내에 완료되지 않으면 timeout 처리한다. (한정된 시간내에 처리 되지 않는 경우 사용한다.)
        if (!await) {
            log.info("timeout");
            throw new RuntimeException("timeout");
        }

        // TaskResult를 가져온다.
        String result = countDownLatchManager.getDataForKey(rechargingMoneyTask.getTaskId());

        MoneyChangingRequest changeStatus = null;
        //Step 4. 증액을 위한 충전 요청 상태 변경 및 기록 (머니)
        if(result.equals("SUCCESS")) {
            //Step 4-1. 성공시 증액 요청 상태를 완료로 변경(머니)
            // 검증 결과 성공시 증액 요청 상태를 완료로 변경 후 신규 레코드로 저장
            changeStatus =
                    changeStatusPort.changeRequestStatus(changeMoneyRequest.getUuid(), ChangingMoneyRequestStatus.SUCCESS);
            // 멤버머니 잔액을 증액(머니)
            MemberMoney memberMoney = increaseMoneyPort.increaseMoney(
                    new MemberMoney.MembershipId(changeMoneyRequest.getMembershipId())
                    , new MemberMoney.MoneyAmount(changeMoneyRequest.getMoneyAmount()));
        } else {
            //step 4-2. 실패시 성공시 증액 요청 상태를 실패로 변경(머니)
            // 결과 실패시 증액요청 상태를 실패로 변경 후 신규 레코드로 저장, 멤버머니 잔액증액은 하지 않는다.
            changeStatus =
                    changeStatusPort.changeRequestStatus(changeMoneyRequest.getUuid(), ChangingMoneyRequestStatus.FAILED);
        }

        return changeStatus;
    }
}
