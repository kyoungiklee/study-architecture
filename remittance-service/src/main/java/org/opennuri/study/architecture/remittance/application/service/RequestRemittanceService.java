package org.opennuri.study.architecture.remittance.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.common.UseCase;
import org.opennuri.study.architecture.common.exception.BusinessCheckFailException;
import org.opennuri.study.architecture.remittance.adapter.out.service.banking.FirmBankingRequest;
import org.opennuri.study.architecture.remittance.adapter.out.service.money.MoneyResponse;
import org.opennuri.study.architecture.remittance.application.port.in.RequestRemittanceCommand;
import org.opennuri.study.architecture.remittance.application.port.in.RequestRemittanceUseCase;
import org.opennuri.study.architecture.remittance.application.port.out.RequestRemittancePort;
import org.opennuri.study.architecture.remittance.application.port.out.banking.BankingServicePort;
import org.opennuri.study.architecture.remittance.application.port.out.membership.MembershipInfo;
import org.opennuri.study.architecture.remittance.application.port.out.membership.MembershipServicePort;
import org.opennuri.study.architecture.remittance.application.port.out.money.MoneyInfo;
import org.opennuri.study.architecture.remittance.application.port.out.money.MoneyServicePort;
import org.opennuri.study.architecture.remittance.common.RemittanceStatus;
import org.opennuri.study.architecture.remittance.common.RemittanceType;
import org.opennuri.study.architecture.remittance.domain.RemittanceRequest;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class RequestRemittanceService implements RequestRemittanceUseCase {

    private final RequestRemittancePort requestRemittancePort;
    private final MembershipServicePort membershipServicePort;
    private final MoneyServicePort moneyServicePort;
    private final BankingServicePort bankingServicePort;

    @Value("${service.banking.firmbanking.from.bank.name}")
    private String fromBankName;

    @Value("${service.banking.firmbanking.from.bank.account.number}")
    private String fromBankAccountNumber;

    @Override
    public RemittanceRequest requestRemittance(RequestRemittanceCommand command) {

        //step1. remittanceRequest를 remittanceStatus가 REQUEST 인 상태로 저장한다..
        RemittanceRequest remittanceRequestHistory = requestRemittancePort.createRemittanceRequestHistory(
                new RemittanceRequest.SenderId(command.getSenderId()),
                new RemittanceRequest.ReceiverId(command.getReceiverId()),
                new RemittanceRequest.ToBankName(command.getToBankName()),
                new RemittanceRequest.ToAccountNumber(command.getToAccountNumber()),
                new RemittanceRequest.RequestType(command.getRequestType()),
                new RemittanceRequest.Amount(command.getAmount()),
                new RemittanceRequest.Description(command.getDescription()),
                new RemittanceRequest.RequestStatus(RemittanceStatus.REQUEST),
                new RemittanceRequest.Uuid(UUID.randomUUID().toString()
                ));

        //step2. senderId 정보로 고객 상태를 조회한다. (MemberShip service)
        MembershipInfo membershipInfo = membershipServicePort.getMembershipStatus(command.getSenderId());

        if (membershipInfo.isValid()) {
            //step2-1. membershipInfo가 유효하면 remittanceStatus를 MEMBERSHIP_CHECK_COMPLETE 변경후 저장한다.
            remittanceRequestHistory = requestRemittancePort.saveRemittanceRequestHistory(remittanceRequestHistory
                    , RemittanceStatus.MEMBERSHIP_CHECK_COMPLETE);
        } else {
            //step2-2. membershipInfo가 유효하지 않으면 remittanceStatus를 MEMBERSHIP_CHECK_FAIL 변경후 저장한다.
            requestRemittancePort.saveRemittanceRequestHistory(remittanceRequestHistory
                    , RemittanceStatus.MEMBERSHIP_CHECK_FAIL);
            throw new BusinessCheckFailException("멤버십 상태가 유효하지 않습니다.");
        }


        //step3. senderId 정보로 MoneyInfo를 조회한다. (Money service)
        MoneyInfo moneyInfo = moneyServicePort.getMoneyInfo(command.getSenderId());
        log.info("moneyInfo: {}", moneyInfo);

        //step3-1. moneyInfo가 유효하면 remittanceStatus를 MONEY_CHECK_COMPLETE 변경후 저장한다.
        if (moneyInfo.isValid()) {
            remittanceRequestHistory = requestRemittancePort.saveRemittanceRequestHistory(remittanceRequestHistory
                    , RemittanceStatus.MONEY_CHECK_COMPLETE);
        } else {
            //step3-2. moneyInfo가 유효하지 않으면 remittanceStatus를 MONEY_CHECK_FAIL 변경후 저장한다.
            requestRemittancePort.saveRemittanceRequestHistory(remittanceRequestHistory
                    , RemittanceStatus.MONEY_CHECK_FAIL);
            throw new BusinessCheckFailException("고객 money가 유효하지 않습니다.");
        }

        //step3-3. moneyInfo의 moneyAmount가 command의 amount보다 작으면 충전요청을 한다.
        if (moneyInfo.getBalance() < command.getAmount()) {
            long rechargingAmount = Long.parseLong(String.valueOf(Math.ceil((command.getAmount() - moneyInfo.getBalance()) / 10000.0) * 10000));

            try {
                //step3-3-1. moneyAmount를 command의 amount로 충전한다.
                MoneyResponse moneyResponse = moneyServicePort.requestMoneyRecharging(command.getSenderId(), rechargingAmount);
                log.info("moneyResponse: {}", moneyResponse);
            } catch (Exception e) {
                //step3-3-2. 충전요청이 실패하면 remittanceStatus를 MONEY_RECHARGING_FAIL 변경후 저장한다.
                requestRemittancePort.saveRemittanceRequestHistory(remittanceRequestHistory
                        , RemittanceStatus.MONEY_RECHARGING_FAIL);
                throw new BusinessCheckFailException("고객 money 충전에 실패하였습니다.");
            }

            //step3-3-3.remittanceStatus를 MONEY_RECHARGING_COMPLETE 변경후 저장한다.
            remittanceRequestHistory = requestRemittancePort.saveRemittanceRequestHistory(remittanceRequestHistory
                    , RemittanceStatus.MONEY_RECHARGING_COMPLETE);
        } else{
            //step3-4 잔액이 부족하지 않으면 송금을 요청한다. remittance-service -> money-service
            if(command.getRequestType() == RemittanceType.INTERNAL) { // 내부송금인 경우
                try {
                    //step4. command의 requestType이 INTERNAL이면 command의 amount를 senderId의 money에서 차감한다.
                    moneyServicePort.requestMoneyDecrease(command.getSenderId(), command.getAmount());
                    //step4-1. command의 requwestType이 INTERNAL이면 command의 amount를 receiverId의 money에 더한다.
                    moneyServicePort.requestMoneyIncrease(command.getReceiverId(), command.getAmount());
                } catch (Exception e) {
                    //step4-2. 송금요청이 실패하면 remittanceStatus를 FAIL 변경후 저장한다.
                    requestRemittancePort.saveRemittanceRequestHistory(remittanceRequestHistory
                            , RemittanceStatus.REMITTANCE_INTERNAL_FAIL);
                    throw new BusinessCheckFailException("고객 money 내부송금에 실패하였습니다.");
                }

                remittanceRequestHistory = requestRemittancePort.saveRemittanceRequestHistory(remittanceRequestHistory
                        , RemittanceStatus.REMITTANCE_INTERNAL_COMPLETE);
            } else { //외부 송금인 경우

                //step5. command의 requestType이 EXTERNAL이면 bankName과 accountNumber로 외부송금을 요청한다.
                FirmBankingRequest firmBankingRequest = FirmBankingRequest.builder()
                        .membershipId(command.getSenderId().toString())
                        .fromBankName(fromBankName)
                        .fromBankAccountNumber(fromBankAccountNumber)
                        .toBankName(command.getToBankName())
                        .toBankAccountNumber(command.getToAccountNumber())
                        .moneyAmount(command.getAmount())
                        .description(command.getDescription())
                        .build();

                boolean isSuccess;
                try {
                    isSuccess = bankingServicePort.requestFirmBanking(firmBankingRequest);
                } catch (Exception e) {
                    requestRemittancePort.saveRemittanceRequestHistory(remittanceRequestHistory
                            , RemittanceStatus.REMITTANCE_EXTERNAL_FAIL);
                    throw new BusinessCheckFailException("고객 money 타행송금에 실패하였습니다.");
                }

                //step5-1. 외부송금이 성공하면 command의 amount를 senderId의 money에서 차감한다.
                if(isSuccess) {
                    MoneyResponse senderMoney = moneyServicePort.requestMoneyDecrease(command.getSenderId(), command.getAmount());

                    log.info("senderMoney: {}", senderMoney);
                    //step5-2. 외부송금이 성공하면 remittanceStatus를 COMPLETE 변경후 저장한다.
                    remittanceRequestHistory = requestRemittancePort.saveRemittanceRequestHistory(remittanceRequestHistory
                            , RemittanceStatus.REMITTANCE_EXTERNAL_COMPLETE);
                } else {
                    //step5-3. 외부송금이 실패하면 remittanceStatus를 FAIL 변경후 저장한다.
                    requestRemittancePort.saveRemittanceRequestHistory(remittanceRequestHistory
                            , RemittanceStatus.REMITTANCE_INTERNAL_FAIL);
                    throw new BusinessCheckFailException("고객 money 타행송금에 실패하였습니다.");
                }
            }
        }
        //step6. remittanceStatus를 COMPLETE 변경후 저장한다.
        return requestRemittancePort.saveRemittanceRequestHistory(remittanceRequestHistory, RemittanceStatus.COMPLETE);
    }
}
