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
import org.opennuri.study.architecture.banking.adapter.out.persistence.FirmBankingRequestStatus;

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

}
