package org.opennuri.study.architecture.common.axon.command;


import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.opennuri.study.architecture.common.SelfValidating;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CheckRegisteredBankAccountCommand extends SelfValidating<CheckRegisteredBankAccountCommand> {

    @TargetAggregateIdentifier
    private String aggregateId;
    @NotNull
    private Long rechargingRequestId;
    @NotNull
    private Long membershipId;
    @NotNull
    private String checkRegisteredBankAccountId;
    @NotNull
    private String bankName;
    @NotNull
    private String bankAccountNumber;
    @NotNull
    private Long amount;

    public CheckRegisteredBankAccountCommand(String aggregateId, Long rechargingRequestId, Long membershipId, String checkRegisteredBankAccountId, String bankName, String bankAccountNumber, Long amount) {
        this.aggregateId = aggregateId;
        this.rechargingRequestId = rechargingRequestId;
        this.membershipId = membershipId;
        this.checkRegisteredBankAccountId = checkRegisteredBankAccountId;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.amount = amount;
        this.validateSelf();
    }

    // builder pattern 사용하기
    public static CheckRegisteredBankAccountCommandBuilder builder() {
        return new CheckRegisteredBankAccountCommandBuilder();
    }

    public static class CheckRegisteredBankAccountCommandBuilder {
        private String aggregateId;
        private Long rechargingRequestId;
        private Long membershipId;
        private String checkRegisteredBankAccountId;
        private String bankName;
        private String bankAccountNumber;
        private Long amount;

        CheckRegisteredBankAccountCommandBuilder() {
        }

        public CheckRegisteredBankAccountCommand.CheckRegisteredBankAccountCommandBuilder aggregateId(String aggregateId) {
            this.aggregateId = aggregateId;
            return this;
        }

        public CheckRegisteredBankAccountCommand.CheckRegisteredBankAccountCommandBuilder rechargingRequestId(Long rechargingRequestId) {
            this.rechargingRequestId = rechargingRequestId;
            return this;
        }

        public CheckRegisteredBankAccountCommand.CheckRegisteredBankAccountCommandBuilder membershipId(Long membershipId) {
            this.membershipId = membershipId;
            return this;
        }

        public CheckRegisteredBankAccountCommand.CheckRegisteredBankAccountCommandBuilder checkRegisteredBankAccountId(String checkRegisteredBankAccountId) {
            this.checkRegisteredBankAccountId = checkRegisteredBankAccountId;
            return this;
        }

        public CheckRegisteredBankAccountCommand.CheckRegisteredBankAccountCommandBuilder bankName(String bankName) {
            this.bankName = bankName;
            return this;
        }

        public CheckRegisteredBankAccountCommand.CheckRegisteredBankAccountCommandBuilder bankAccountNumber(String bankAccountNumber) {
            this.bankAccountNumber = bankAccountNumber;
            return this;
        }

        public CheckRegisteredBankAccountCommand.CheckRegisteredBankAccountCommandBuilder amount(Long amount) {
            this.amount = amount;
            return this;
        }

        public CheckRegisteredBankAccountCommand build() {
            return new CheckRegisteredBankAccountCommand(aggregateId, rechargingRequestId, membershipId, checkRegisteredBankAccountId, bankName, bankAccountNumber, amount);
        }

    }

    @Override
    public String toString() {
        return "CheckRegisteredBankAccountCommand{" +
                "aggregateId='" + aggregateId + '\'' +
                ", rechargingRequestId=" + rechargingRequestId +
                ", MembershipId=" + membershipId +
                ", checkRegisteredBankAccountId='" + checkRegisteredBankAccountId + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", amount=" + amount +
                '}';
    }
}
