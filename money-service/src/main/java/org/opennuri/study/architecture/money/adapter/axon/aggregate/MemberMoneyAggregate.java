package org.opennuri.study.architecture.money.adapter.axon.aggregate;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.ApplyMore;
import org.axonframework.spring.stereotype.Aggregate;
import org.opennuri.study.architecture.money.adapter.axon.command.MemberMoneyCreateCommand;
import org.opennuri.study.architecture.money.adapter.axon.command.MemberMoneyIncreaseCommand;
import org.opennuri.study.architecture.money.adapter.axon.event.MemberMoneyCreateEvent;
import org.opennuri.study.architecture.money.adapter.axon.event.MemberMoneyIncreaseEvent;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@Aggregate
@NoArgsConstructor
@Data
public class MemberMoneyAggregate {
    @AggregateIdentifier
    private String id;
    private Long membershipId;
    private Long balance;

    @Override
    public String toString() {
        return "MemberMoneyAggregate{" +
                "id='" + id + '\'' +
                ", membershipId=" + membershipId +
                ", balance=" + balance +
                '}';
    }

    /**
     * aggregate 생성
     * @param command MemberMoneyCreateCommand
     */
    @CommandHandler
    public MemberMoneyAggregate(MemberMoneyCreateCommand command) {
        log.info("MemberMoneyCreateCommand: {}", command);
        ApplyMore apply = apply(new MemberMoneyCreateEvent(command.getMembershipId(), command.getBalance()));
        apply.andThen(() -> log.info("MemberMoneyCreateCommand applied"));
        log.info("MemberMoneyAggregate: {}", this);
    }

    @EventSourcingHandler
    public void on(MemberMoneyCreateEvent event) {
        log.info("MemberMoneyCreateEvent: {}", event);
        this.id = UUID.randomUUID().toString();
        this.membershipId = event.getMembershipId();
        this.balance = event.getBalance();
        log.info("MemberMoneyAggregate: {}", this);
    }

    @CommandHandler
    public String increaseMoney(MemberMoneyIncreaseCommand command) {
        id = command.getAggregateId();
        log.info("MemberMoneyIncreaseCommand: {}", command);
        ApplyMore apply = apply(new MemberMoneyIncreaseEvent(id, command.getMembershipId(), command.getAmount()));
        apply.andThen(() -> log.info("MemberMoneyIncreaseCommand applied"));
        log.info("This.Id: {}", this.id);
        return id;
    }

    @EventSourcingHandler
    public void on(MemberMoneyIncreaseEvent event) {
        log.info("MemberMoneyIncreaseEvent: {}", event);
        id = event.getAggregateId();
        membershipId = event.getMembershipId();
        balance = event.getAmount();
    }
}

