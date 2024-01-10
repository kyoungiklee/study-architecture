package org.opennuri.study.architecture.money.adapter.axon.saga;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.jetbrains.annotations.NotNull;
import org.opennuri.study.architecture.common.axon.command.CheckRegisteredBankAccountCommand;
import org.opennuri.study.architecture.common.axon.event.CheckedRegisteredBankAccountEvent;
import org.opennuri.study.architecture.money.adapter.axon.event.RechargingRequestCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Slf4j
@Saga
@NoArgsConstructor
public class MoneyRechargeSaga {
    private transient CommandGateway commandGateway;

    @Autowired
    public void setCommandGateway(@NotNull CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    /**
     * 충전 요청이 생성되었다는 이벤트를 핸들링한다.
     * money-service에서 발행된 이벤트로 충전 요청이 생성되었다는 것을 의미한다.
     * 충전 요청이 생성되면 banking-service로 계좌 등록 여부를 확인하는 커맨드를 발행한다.
     * @param event RechargingRequestCreatedEvent 충전 요청 생성 이벤트
     */
    @StartSaga
    @SagaEventHandler(associationProperty = "rechargingRequestAssociationId") // 충전요청 command <--> event 연관관계 ID
    public void handle(RechargingRequestCreatedEvent event) {  //충전 요청이 생성되었다는 이벤트
        log.info("RechargingRequestCreatedEvent: {}", event);
        String checkRegisteredBankAccountAssociationId = UUID.randomUUID().toString();

        SagaLifecycle.associateWith("checkRegisteredBankAccountAssociationId", checkRegisteredBankAccountAssociationId);

        //step-1 Money-service에서 banking-service로 계좌 등록 여부를 확인한다. (CheckRegisteredBankAccountCommand)
        commandGateway.send(
                CheckRegisteredBankAccountCommand.builder()
                        //registeredBankAccountAggregate 를 요청하므로 RegisteredBankAccountAggregateId를 전달한다.
                        .aggregateId(event.getRegisteredBankAccountAggregateId())
                        .rechargingRequestAssociationId(event.getRechargingRequestAssociationId())
                        .membershipId(event.getMembershipId())
                        .bankName(event.getBankName())
                        .bankAccountNumber(event.getBankAccountNumber())
                        .amount(event.getAmount())
                        .checkRegisteredBankAccountAssociationId(checkRegisteredBankAccountAssociationId)
                        .build()
        ).whenComplete((result, throwable) -> {
            if (throwable != null) {
                log.error("CheckRegisteredBankAccountCommand: {}", throwable.getMessage());
            } else {
                log.info("CheckRegisteredBankAccountCommand: {}", result);
            }
        });
    }

    @SagaEventHandler(associationProperty = "checkRegisteredBankAccountAssociationId")
    public void handle(CheckedRegisteredBankAccountEvent event) { //banking-service에서 계좌 등록 여부를 확인한 결과를 받는다. (FirmBankingResult)
        log.info("CheckedRegisteredBankAccountEvent: {}", event);

        boolean isChecked = event.isChecked();
        if (isChecked) {
            log.info("CheckedRegisteredBankAccountEvent: {}", "계좌 등록 여부 확인 완료");
        } else {
            log.info("CheckedRegisteredBankAccountEvent: {}", "계좌 등록 여부 확인 실패");
        }

        String requestFirmBankingAggregateId = UUID.randomUUID().toString();
        SagaLifecycle.associateWith("requestFirmBankingAggregateId", requestFirmBankingAggregateId);

        //firm banking 요청 고객계좌 --> 법인계좌
        /*commandGateway.send(RequestFirmBankingCommand.builder()
                        .aggregateId(requestFirmBankingAggregateId)
                        .requestFirmBankingId(event.getRechargingRequestId())
                        .membershipId(event.getMembershipId())
                        .fromBankName(event.getBankName())
                        .fromBankAccountNumber(event.getBankAccountNumber())
                        .toBankName("OpenNuri Bank")
                        .toBankAccountNumber("1234567890")
                        .moneyAmount(event.getAmount())
                        .build())
                .whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        log.error("RequestFirmBankingCommand: {}", throwable.getMessage());
                    } else {
                        log.info("RequestFirmBankingCommand: {}", result);
                    }
                });*/

        SagaLifecycle.end();

    }

    /*@SagaEventHandler(associationProperty = "requestFirmBankingAggregateId")
    public void handle(RequestFirmbankingFinishedEvent event, IncreaseMoneyPort increaseMoneyPort) { //banking-service에서 firm banking을 요청한 결과를 받는다. (FirmBankingResult)
        log.info("RequestFirmbankingFinishedEvent: {}", event);
        boolean status = event.isFinished();
        if (status) {
            log.info("RequestFirmbankingFinishedEvent: {}", "firm banking 요청 완료");
        } else {
            log.info("RequestFirmbankingFinishedEvent: {}", "firm banking 요청 실패");
        }

        //DB에 충전 금액을 반영한다.
        MemberMoney memberMoney = increaseMoneyPort.increaseMoney(new MemberMoney.MembershipId(Long.parseLong(event.getMembershipId()))
                , event.getMoneyAmount());
        if (memberMoney != null) {
            log.info("increaseMoneyPort: {}", memberMoney);
            // 성공시 saga 종료
            SagaLifecycle.end();
        } else {
            log.info("increaseMoneyPort: {}", "DB 업데이트 실패");
            // 실패시 롤백 이벤트 발행
            String rollbackFirmBankingAggregateId = UUID.randomUUID().toString();
            SagaLifecycle.associateWith("rollbackFirmBankingAggregateId", rollbackFirmBankingAggregateId);

            commandGateway.send(RollbackFirmBankingRequestCommand.builder()
                            .rollbackFirmBankingRequestId(event.getRequestFirmBankingId())
                            .aggregateId(rollbackFirmBankingAggregateId)
                            .rechargingRequestId(event.getRechargingRequestId())
                            .fromBankName(event.getFromBankName())
                            .fromBankAccountNumber(event.getFromBankAccountNumber())
                            .toBankName(event.getToBankName())
                            .toBankAccountNumber(event.getToBankAccountNumber())
                            .amount(event.getMoneyAmount())
                            .build())
                    .whenComplete((result, throwable) -> {
                        if (throwable != null) {
                            log.error("RollbackFirmBankingRequestCommand: {}", throwable.getMessage());
                        } else {
                            log.info("RollbackFirmBankingRequestCommand: {}", result);
                            SagaLifecycle.end();
                        }
                    });
        }
    }*/

    /*@EndSaga
    @SagaEventHandler(associationProperty = "rollbackFirmBankingAggregateId")
    public void handle(RollbackFirmbankingFinishedEvent event) { //money-service에서 banking-service에 rollback을 요청한다. (RollbackFirmBankingCommand)
        log.info("RollbackFirmBankingRequestCommand: {}", event);
    }*/

    //step-2 banking-service에서 계좌 등록 여부를 확인한 결과를 받는다. (FirmBankingResult)

    //step-3 money-service에서 banking-service로 firm banking을 요청한다. (RequestFirmBankingCommand)

    //step-4 banking-service에서 firm banking을 요청한 결과를 받는다. (FirmBankingResult)

    //step-5 money-service에서 충전 요청을 완료한다. (CompleteRechargingRequestCommand)

    //step-6 money-service에서 DB 업데이트 실패 시 banking-service에 rollback을 요청한다. (RollbackFirmBankingCommand)
}
