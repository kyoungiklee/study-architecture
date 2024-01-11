package org.opennuri.study.architecture.banking.adapter.axon.aggregate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.opennuri.study.architecture.banking.adapter.axon.command.CreateFirmbankingRequestCommand;
import org.opennuri.study.architecture.banking.adapter.axon.command.UpdateFirmBankingRequestCommand;
import org.opennuri.study.architecture.banking.adapter.axon.event.FirmbankingRequestCreatedEvent;
import org.opennuri.study.architecture.banking.adapter.axon.event.FirmbankingRequestUpdatedEvent;
import org.opennuri.study.architecture.banking.adapter.out.external.bank.ExternalFirmBankingRequest;
import org.opennuri.study.architecture.banking.adapter.out.external.bank.FirmBankingResult;
import org.opennuri.study.architecture.banking.adapter.out.persistence.FirmBankingRequestStatus;
import org.opennuri.study.architecture.banking.appication.port.out.RequestExternalFirmBankingPort;
import org.opennuri.study.architecture.banking.appication.port.out.RequestFirmBankingPort;
import org.opennuri.study.architecture.banking.domain.FirmBankingRequest;
import org.opennuri.study.architecture.common.axon.command.RequestFirmBankingCommand;
import org.opennuri.study.architecture.common.axon.command.RollbackFirmBankingRequestCommand;
import org.opennuri.study.architecture.common.axon.event.RequestFirmbankingFinishedEvent;
import org.opennuri.study.architecture.common.axon.event.RollbackFirmbankingFinishedEvent;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@Aggregate
@NoArgsConstructor
@Data
public class FirmbankingRequestAggregate {

    @AggregateIdentifier
    private String id;
    private String toBankName;
    private String toBankAccountNumber;
    private String fromBankName;
    private String fromBankAccountNumber;
    private Long amount;
    private FirmBankingRequestStatus firmbankingStatus;

    @CommandHandler
    public FirmbankingRequestAggregate(CreateFirmbankingRequestCommand command) {
        log.info("FirmbankingRequestAggregate CreateFirmbankingRequestCommand: {}", command);
        apply(new FirmbankingRequestCreatedEvent(command.getToBankName()
                , command.getToBankAccountNumber()
                , command.getFromBankName()
                , command.getFromBankAccountNumber()
                , command.getAmount()));
    }



    @EventSourcingHandler
    public void on(FirmbankingRequestCreatedEvent event) {
        log.info("FirmbankingRequestAggregate FirmbankingRequestCreatedEvent: {}", event);
        this.id = UUID.randomUUID().toString();
        this.toBankName = event.getToBankName();
        this.toBankAccountNumber = event.getToBankAccountNumber();
        this.fromBankName = event.getFromBankName();
        this.fromBankAccountNumber = event.getFromBankAccountNumber();
        this.amount = event.getAmount();
    }

    @CommandHandler
    public String handel(UpdateFirmBankingRequestCommand command) {
        log.info("FirmbankingRequestAggregate UpdataFirmBankingRequestCommand: {}", command);
        id = command.getAggregateId();
        apply(new FirmbankingRequestUpdatedEvent(command.getAggregateId(), command.getStatus()));
        return id;
    }

    @EventSourcingHandler
    public void on(FirmbankingRequestUpdatedEvent event) {
        log.info("FirmbankingRequestAggregate FirmbankingRequestUpdatedEvent: {}", event);
        this.firmbankingStatus = event.getStatus();
    }

    /**
     * money-service에서 banking-service로 firmbanking 요청을 핸들링한다. (RequestFirmBankingCommand)
     *
     * @param command RequestFirmBankingCommand firmbanking 요청
     */
    @CommandHandler
    public FirmbankingRequestAggregate(RequestFirmBankingCommand command
            , RequestFirmBankingPort firmBankingPort
            , RequestExternalFirmBankingPort requestExternalFirmBankingPort) {
        log.info("FirmbankingRequestAggregate RequestFirmBankingCommand: {}", command);
        id = command.getAggregateId();

        //고객계좌로부터 법인계좌로 firmbanking 요청을 한다.
        firmBankingPort.createFirmBankingRequest(
                new FirmBankingRequest.MembershipId(command.getMembershipId()),
                new FirmBankingRequest.FromBankName(command.getFromBankName()),
                new FirmBankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                new FirmBankingRequest.ToBankName(command.getToBankName()),
                new FirmBankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
                new FirmBankingRequest.MoneyAmount(command.getMoneyAmount()),
                new FirmBankingRequest.RequestStatus(FirmBankingRequestStatus.REQUESTED),
                new FirmBankingRequest.RejectReason(""),
                new FirmBankingRequest.Description(command.getDescription()),
                new FirmBankingRequest.Uuid(UUID.randomUUID().toString()),
                new FirmBankingRequest.AggregateId(command.getAggregateId())
        );
        //firmbanking 요청을 외부 시스템에 전달한다.
        FirmBankingResult firmBankingResult = requestExternalFirmBankingPort.requestExternalFirmBanking(
                ExternalFirmBankingRequest.builder()
                        .membershipId(command.getMembershipId())
                        .fromBankName(command.getFromBankName())
                        .fromBankAccountNumber(command.getFromBankAccountNumber())
                        .toBankName(command.getToBankName())
                        .toBankAccountNumber(command.getToBankAccountNumber())
                        .moneyAmount(command.getMoneyAmount())
                        .description(command.getDescription())
                        .build()
        );
        //firmbanking 요청 결과를 이벤트로 발행한다.
        FirmBankingResult.FirmBankingResultCode resultCode = firmBankingResult.getResultCode(); //firm banking 요청 결과

        //firm banking 요청 결과를 이벤트로 발행한다. (baking-service ->saga --> money-service)
        apply(RequestFirmbankingFinishedEvent.builder()
                .requestFirmBankingAggregateId(command.getAggregateId())
                .membershipId(command.getMembershipId())
                .fromBankName(command.getFromBankName())
                .fromBankAccountNumber(command.getFromBankAccountNumber())
                .toBankName(command.getToBankName())
                .toBankAccountNumber(command.getToBankAccountNumber())
                .amount(command.getMoneyAmount())
                .finished(resultCode == FirmBankingResult.FirmBankingResultCode.SUCCESS)
                .build());
    }

    @CommandHandler
    public FirmbankingRequestAggregate(RollbackFirmBankingRequestCommand command, RequestFirmBankingPort firmBankingPort, RequestExternalFirmBankingPort requestExternalFirmBankingPort) {
        log.info("FirmbankingRequestAggregate RollbackFirmBankingRequestCommand: {}", command);
        id = command.getAggregateId();

        // rollback 요청을 수행한다. 법인계좌 -> 고객계좌 송금처리
        // firmbanking 요청내용을 저장한다.
        firmBankingPort.createFirmBankingRequest(
                new FirmBankingRequest.MembershipId(command.getMembershipId()),
                new FirmBankingRequest.FromBankName(command.getFromBankName()),
                new FirmBankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                new FirmBankingRequest.ToBankName(command.getToBankName()),
                new FirmBankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
                new FirmBankingRequest.MoneyAmount(command.getAmount()),
                new FirmBankingRequest.RequestStatus(FirmBankingRequestStatus.REJECTED),
                new FirmBankingRequest.RejectReason(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()),
                new FirmBankingRequest.Description(command.getDescription()),
                new FirmBankingRequest.Uuid(UUID.randomUUID().toString()),
                new FirmBankingRequest.AggregateId(command.getAggregateId()));

        // firmbanking 요청을 외부 시스템에 전달한다.
        FirmBankingResult firmBankingResult = requestExternalFirmBankingPort.requestExternalFirmBanking(
                ExternalFirmBankingRequest.builder()
                        .membershipId(command.getMembershipId())
                        .fromBankName(command.getFromBankName())
                        .fromBankAccountNumber(command.getFromBankAccountNumber())
                        .toBankName(command.getToBankName())
                        .toBankAccountNumber(command.getToBankAccountNumber())
                        .moneyAmount(command.getAmount())
                        .description(command.getDescription())
                        .build());

        // firmbanking 요청 결과를 이벤트로 발행한다.
        apply(RollbackFirmbankingFinishedEvent.builder()
                .rollbackFirmBankingAggregateId(command.getAggregateId())
                .membershipId(command.getMembershipId())
                .build());

    }


}
