package org.opennuri.study.architecture.money.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.opennuri.study.architecture.common.CountDownLatchManager;
import org.opennuri.study.architecture.common.UseCase;
import org.opennuri.study.architecture.common.exception.BusinessException;
import org.opennuri.study.architecture.common.task.RechargingMoneyTask;
import org.opennuri.study.architecture.common.task.SubTask;
import org.opennuri.study.architecture.money.application.port.in.DecreaseMoneyRequestCommand;
import org.opennuri.study.architecture.money.application.port.in.DecreaseMoneyRequestUseCase;
import org.opennuri.study.architecture.money.application.port.out.DecreaseMoneyPort;
import org.opennuri.study.architecture.money.application.port.out.FindMemberMoneyPort;
import org.opennuri.study.architecture.money.application.port.out.IncreaseMoneyPort;
import org.opennuri.study.architecture.money.application.port.out.kafka.SendRechargingMoneyTaskPort;
import org.opennuri.study.architecture.money.domain.ChangingMoneyRequestStatus;
import org.opennuri.study.architecture.money.domain.ChangingMoneyRequestType;
import org.opennuri.study.architecture.money.domain.MemberMoney;
import org.opennuri.study.architecture.money.domain.MoneyChangingRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DecreaseMoneyRequestService implements DecreaseMoneyRequestUseCase {
    private final DecreaseMoneyPort decreaseMoneyPort;
    private final IncreaseMoneyPort increaseMoneyPort;
    private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;
    private final CountDownLatchManager countDownLatchManager;
    private final FindMemberMoneyPort findMemberMoneyPort;
    private final RestTemplate restTemplate;

    @Value("${service.banking.url}")
    private String bankingUrl;

    @Value("${service.banking.firmbanking.accountNumber}")
    private String  corporationAccountNumber;
    @Value("${service.banking.firmbanking.accountName}")
    private String  corporationAccountName;



    @Override
    @Transactional
    public MemberMoney decreaseMoneyRequest(DecreaseMoneyRequestCommand command) {

        String membershipId;
        try {
            membershipId = String.valueOf(command.getMembershipId());
        } catch (Exception e) {
            log.error("decreaseMoneyRequestInternal error", e);
            throw new IllegalArgumentException("membershipId is null");
        }

        // 고객 요청 UUID 생성(moneyChangingRequestId는 요청이 생성 될 때 자동 생성되므로,
        // 해당 건으로 연결된 요청내용으로 확인 할 수 있도록 UUID를 생성한다.)
        String uuid = UUID.randomUUID().toString();

        //1. 요청정보를 요청상태로 저장한다. (머니)
        saveChangingMoneyStatus(command
                , ChangingMoneyRequestStatus.REQUESTED, uuid);

        //2. 고객의 상태가 정상인지, 연동계좌가 정상인지 확인한다. (회원, 뱅킹)
        String result = kafkaTaskProduce(command, membershipId, uuid);

        log.info("result: {}", result);

        // 고객상태확인 및 연결계좌상태 확인이 성공적하면 고객의 Money 잔액을 감소하는 작업을 진행한다.
        if(result.equals("SUCCESS")) {
            //3. 고객의 Money 잔액이 충분한지 확인한다. (머니)
            MemberMoney memberMoney = findMemberMoneyPort.findMemberMoney(command.getMembershipId());
            log.info("memberMoney: {}", memberMoney);

            // 잔액이 충분하지 않으면
            if(memberMoney.getMoneyAmount() < command.getMoneyAmount()) {
                //3. 충분하지 않으면, 고객 연결 계좌로부터 충전을 요청한다. (뱅킹)
                FirmBankingResponse firmBankingResponse = rechargingMembershipMoney(command, memberMoney, membershipId);
                log.info("firmBankingResponse: {}", firmBankingResponse);

                //3-1 고객연결계좌로부터 법인계좌 입금이 실패하였다면 요청정보의 상태를 실패로 변경한다. (머니)
                if(firmBankingResponse.getResultCode().equals(FirmBankingResponse.FirmBankingResultCode.FAIL)) {
                    saveChangingMoneyStatus(command, ChangingMoneyRequestStatus.TRANSFER_FIRM_BANKING_FAILED, uuid);
                    throw new BusinessException("머니 충전에 실패하였습니다.");
                }else{
                    //3-2 고객의 Money 잔액을 충전요청한 금액 만큼 증액한다.(머니)
                    MemberMoney increaseMoney = increaseMoneyPort.increaseMoney(
                            new MemberMoney.MembershipId(command.getMembershipId())
                            , new MemberMoney.MoneyAmount(firmBankingResponse.getMoneyAmount()));

                    if(increaseMoney == null) {
                        //3-2-1 고객의 Money 잔액을 충전요청한 금액 만큼 증액하는데 실패하였다면 요청정보의 상태를 실패로 변경한다. (머니)
                        saveChangingMoneyStatus(command, ChangingMoneyRequestStatus.TRANSFER_FIRM_BANKING_FAILED, uuid);
                        throw new BusinessException("머니 충전에 실패하였습니다.");
                    }

                    //3-3 고객의 Money 잔액을 사용요청한 금액 만큼 감소시킨다. (머니)
                    MemberMoney decreaseMoney = decreaseMoneyPort.decreaseMoney(
                            new MemberMoney.MembershipId(command.getMembershipId())
                            , new MemberMoney.MoneyAmount(command.getMoneyAmount()));

                    if (decreaseMoney == null) {
                        //3-3-1 고객의 Money 잔액을 사용요청한 금액 만큼 감소시키는데 실패하였다면 요청정보의 상태를 실패로 변경한다. (머니)
                        saveChangingMoneyStatus(command, ChangingMoneyRequestStatus.TRANSFER_FIRM_BANKING_FAILED, uuid);
                        throw new BusinessException("머니 충전에 실패하였습니다.");
                    }

                    //3-4 요청정보의 상태를 완료로 변경한다. (머니)
                    saveChangingMoneyStatus(command, ChangingMoneyRequestStatus.SUCCESS, uuid);

                    //3-5 고객의 Money 잔액을 반환한다. (머니)
                    return decreaseMoney;
                }

            } else {
                //4-2 충분하면, 고객의 Money 잔액을 감소시킨다. (머니)
                MemberMoney decreaseMoney = decreaseMoneyPort.decreaseMoney(
                        new MemberMoney.MembershipId(command.getMembershipId())
                        , new MemberMoney.MoneyAmount(command.getMoneyAmount()));
                //5. 요청정보의 상태를 완료로 변경한다. (머니)
                saveChangingMoneyStatus(command, ChangingMoneyRequestStatus.SUCCESS, uuid);
                return decreaseMoney;
            }

        } else {
            //고객정보 유효성 체크 결과를 확인한다.
            if(result.equals("MEMBERSHIP_CHECK_FAILED")) {
                //요청정보의 상태를 실패로 변경한다. (머니)
                saveChangingMoneyStatus(command, ChangingMoneyRequestStatus.MEMBERSHIP_CHECK_FAILED, uuid);
                throw new BusinessException("고객정보 유효성 체크에 실패하였습니다.");
            }
            //연동계좌 유효성 체크 결과를 확인한다.
            else if(result.equals("ACCOUNT_CHECK_FAILED")) {
                //요청정보의 상태를 실패로 변경한다. (머니)
                saveChangingMoneyStatus(command, ChangingMoneyRequestStatus.ACCOUNT_CHECK_FAILED, uuid);
                throw new BusinessException("연동계좌 유효성 체크에 실패하였습니다.");
            } else {
                //요청정보의 상태를 실패로 변경한다. (머니)
                saveChangingMoneyStatus(command, ChangingMoneyRequestStatus.FAILED, uuid);
                throw new BusinessException("머니 감액에 실패하였습니다.");
            }
        }
    }

    /**
     * 요청정보를 각 단계 처리 결과에때라 히스토리를 저장한다.
     *
     * @param command   요청정보
     * @param requested 요청상태
     * @param uuid      요청UUID
     */
    private void  saveChangingMoneyStatus(DecreaseMoneyRequestCommand command, ChangingMoneyRequestStatus requested, String uuid) {
        MoneyChangingRequest changeMoneyRequest = decreaseMoneyPort.createChangeMoneyRequest(
                new MoneyChangingRequest.MembershipId(command.getMembershipId())
                , new MoneyChangingRequest.RequestType(ChangingMoneyRequestType.WITHDRAW)
                , new MoneyChangingRequest.MoneyAmount(command.getMoneyAmount())
                , new MoneyChangingRequest.RequestStatus(requested)
                , new MoneyChangingRequest.RequestDateTime(LocalDateTime.now())
                , new MoneyChangingRequest.UUID(uuid));

        if (changeMoneyRequest == null) {
            throw new BusinessException("머니요청 정보 저장에 실패하였습니다.");
        }
    }

    /**
     * 고객의 상태가 정상인지, 연동계좌가 정상인지 kafka로 비동기 방식을 사용하여 확인한다.
     * <p>
     *     kafka로 요청할 task 생성(고객 확인 / 연동계좌 확인) 후 produce하여 비동기로 결과를 받는다
     *     TaskConsumer에서 요청내용을 처리후 task.result.topic으로 결과를 produce하면 ResultConsumer consume하여
     *     countDownLatchManager에 결과를 저장한다.</br>
     *     countDownLatchManager에 결과가 저장되면 countDown을 실행하고 countDownLatchManager에서 결과를 가져온다.</br>
     * @param command 요청정보
     * @param membershipId  고객ID
     * @param uuid  요청UUID
     * @return  요청결과
     */
    @NotNull
    private String kafkaTaskProduce(DecreaseMoneyRequestCommand command, String membershipId, String uuid) {
        //1. 고객의 상태가 정상인지 확인용 subTask 생성. (회원)
        //taskName을 properties로 관리할 수 있도록 변경
        SubTask membershipCheckTask = SubTask.builder()
                .membershipId(membershipId)
                .subTaskName("고객정보 유효성 체크")
                .subTaskType(SubTask.SubTaskType.MEMBERSHIP)
                .subTaskStatus(SubTask.SubTaskStatus.STARTED)
                .build();
        //2. 고객에게 연동된 계좌 정상 여부 확인용 subTask 생성 (뱅킹)
        SubTask accountCheckTask = SubTask.builder()
                .membershipId(membershipId)
                .subTaskName("연결계좌 유효성 체크")
                .subTaskType(SubTask.SubTaskType.BANKING)
                .subTaskStatus(SubTask.SubTaskStatus.STARTED)
                .build();

        List<SubTask> subTasks = List.of(membershipCheckTask, accountCheckTask);

        //3.kafka로 produce 요청 task 생성(고객 확인 / 연동계좌 확인)
        RechargingMoneyTask rechargingMoneyTask = RechargingMoneyTask.builder()
                .taskId(uuid)
                .taskName("머니 감액 작업")
                .membershipId(membershipId)
                .moneyAmount(command.getMoneyAmount())
                .subTasks(subTasks)
                .build();

        log.info("rechargingMoneyTask: {}", rechargingMoneyTask);
        //4. kafka로 전송한다.
        sendRechargingMoneyTaskPort.sendRechargingMoneyTask(rechargingMoneyTask);
        //5. CountDownLatch 생성한다. (TaskId를 key로 하여 CountDownLatch를 생성한다.)
        countDownLatchManager.addCountDownLatch(rechargingMoneyTask.getTaskId(), 1);

        //6. 완료될때까지 기다린다.(kafka TaskConsumer에서 요청내용을 처리후 task.result.topic으로 결과를 produce한다.
        countDownLatchManager.await(rechargingMoneyTask.getTaskId());

        //7. TaskConsumer에서 produce한 처리결과를 ResultConsumer consume하여 countDownLatchManager에 결과를 저장한다. (countDownLatchManager에 결과가 저장되면 countDown을 실행한다.)
        return countDownLatchManager.getResult(rechargingMoneyTask.getTaskId()).orElse("FAIL");
    }

    /**
     * 고객의 Money 잔액이 충분한지 확인한다.
     * <p>
     * 충분하지 않으면 고객 연결 계좌로부터 충전을 요청한다.</br>
     * banking-service의 /banking/firmbanking/request로 요청한다.
     *
     * @param command      요청정보
     * @param memberMoney  고객 Money 정보
     * @param membershipId 고객ID
     * @return 충분하면 true, 부족하면 false
     */
    private FirmBankingResponse rechargingMembershipMoney(
            DecreaseMoneyRequestCommand command // 요청정보
            , MemberMoney memberMoney // 고객 Money 정보
            , String membershipId // 고객ID
    ) {
        //1 고객 계좌 정보를 조회한다. (뱅킹)
        String accountUrl = String.join("/", bankingUrl, "banking/account", membershipId);
        log.info("accountUrl: {}", accountUrl);

        BankingAccountResponse membershipAccount = restTemplate.getForObject(accountUrl, BankingAccountResponse.class);
        log.info("membershipAccount: {}", membershipAccount);

        //2 고객 연결 계좌로 충전을 요청한다. (뱅킹)
        String firmBankingUrl = String.join("/", bankingUrl, "banking/firmbanking/request");
        log.info("firmBankingUrl: {}", firmBankingUrl);

        long transferRequestAmount = Math.abs(memberMoney.getMoneyAmount() - command.getMoneyAmount());

        // 충전요청금액이 1만원 미만이면 1만원으로 변경한다.
        if( transferRequestAmount < 10_000L) {
            transferRequestAmount = 10_000L;
        }

        return restTemplate.postForObject(firmBankingUrl, FirmBankingRequest.builder()
                .membershipId(membershipId)
                .fromBankName(Objects.requireNonNull(membershipAccount).getBankName())
                .fromBankAccountNumber(membershipAccount.getBankAccountNumber())
                .toBankName(corporationAccountName)
                .toBankAccountNumber(corporationAccountNumber)
                .moneyAmount(transferRequestAmount)
                .description("머니 충전")
                .build(), FirmBankingResponse.class);
    }
}
