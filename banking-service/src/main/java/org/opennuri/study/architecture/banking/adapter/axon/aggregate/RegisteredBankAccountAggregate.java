package org.opennuri.study.architecture.banking.adapter.axon.aggregate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.opennuri.study.architecture.banking.adapter.axon.command.CreateRegisteredBankAccountCommand;
import org.opennuri.study.architecture.banking.adapter.axon.event.RegisteredBankAccountCreatedEvent;
import org.opennuri.study.architecture.banking.appication.port.out.FindBankAccountPort;
import org.opennuri.study.architecture.banking.domain.RegisteredBankAccount;
import org.opennuri.study.architecture.common.axon.command.CheckRegisteredBankAccountCommand;
import org.opennuri.study.architecture.common.axon.event.CheckedRegisteredBankAccountEvent;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
@Slf4j
@Aggregate
@NoArgsConstructor
@Data
public class RegisteredBankAccountAggregate {
    @AggregateIdentifier
    private String id;
    private Long membershipId;
    private String bankName;
    private String bankAccountNumber;

    @CommandHandler
    public RegisteredBankAccountAggregate(CreateRegisteredBankAccountCommand command) {
        apply(new RegisteredBankAccountCreatedEvent(command.getMembershipId(), command.getBankName(), command.getBankAccountNumber()));
    }

    @EventSourcingHandler
    public void on(RegisteredBankAccountCreatedEvent event) {
        log.info("event: {}", event);
        this.id = UUID.randomUUID().toString();
        this.membershipId = event.getMembershipId();
        this.bankName = event.getBankName();
        this.bankAccountNumber = event.getBankAccountNumber();
    }

    /**
     * money-service에서 banking-service로 계좌 등록 여부 요청을 핸들링한다. (CheckRegisteredBankAccountCommand)
     * @param command CheckRegisteredBankAccountCommand 계좌 등록 여부 확인 요청
     */
    @CommandHandler
    public void handle(CheckRegisteredBankAccountCommand command, FindBankAccountPort findBankAccountPort) {
        log.info("CheckedRegisteredBankAccountEvent: {}", command);

        // 고객의 계좌 상태를 확인하여 이벤트를 발행한다.
        id = command.getAggregateId(); // memberMoneyAggregateId

        RegisteredBankAccount bankAccount = findBankAccountPort.findBankAccount(command.getBankName(), command.getBankAccountNumber());
        boolean isValidLinkedStatus = bankAccount.isValidLinkedStatus();

        String firmBankingRequestAggregateId = UUID.randomUUID().toString();
        // 계좌 확인 이벤트를 발행한다.
        apply(CheckedRegisteredBankAccountEvent.builder()
                .checkRegisteredBankAccountAssociationId(command.getCheckRegisteredBankAccountAssociationId())
                .membershipId(command.getMembershipId())
                .bankName(command.getBankName())
                .bankAccountNumber(command.getBankAccountNumber())
                .amount(command.getAmount())
                .firmBankingRequestAggregateId(firmBankingRequestAggregateId)
                .isChecked(isValidLinkedStatus)
                .build()
        );
    }




}
