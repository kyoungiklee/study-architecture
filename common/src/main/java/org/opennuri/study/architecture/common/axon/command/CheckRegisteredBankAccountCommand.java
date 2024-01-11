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
    private String rechargingRequestAssociationId;
    @NotNull
    private Long membershipId;
    @NotNull
    private String checkRegisteredBankAccountAssociationId;
    @NotNull
    private String bankName;
    @NotNull
    private String bankAccountNumber;
    @NotNull
    private Long amount;

    public CheckRegisteredBankAccountCommand(String aggregateId
            , String rechargingRequestAssociationId
            , Long membershipId
            , String checkRegisteredBankAccountAssociationId
            , String bankName
            , String bankAccountNumber
            , Long amount) {
        this.aggregateId = aggregateId;
        this.rechargingRequestAssociationId = rechargingRequestAssociationId;
        this.membershipId = membershipId;
        this.checkRegisteredBankAccountAssociationId = checkRegisteredBankAccountAssociationId;
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
        private String rechargingRequestAssociationId;
        private Long membershipId;
        private String checkRegisteredBankAccountAssociationId;
        private String bankName;
        private String bankAccountNumber;
        private Long amount;

        CheckRegisteredBankAccountCommandBuilder() {
        }

        public CheckRegisteredBankAccountCommand.CheckRegisteredBankAccountCommandBuilder aggregateId(String aggregateId) {
            this.aggregateId = aggregateId;
            return this;
        }

        public CheckRegisteredBankAccountCommand.CheckRegisteredBankAccountCommandBuilder rechargingRequestAssociationId(String rechargingRequestAssociationId) {
            this.rechargingRequestAssociationId = rechargingRequestAssociationId;
            return this;
        }

        public CheckRegisteredBankAccountCommand.CheckRegisteredBankAccountCommandBuilder membershipId(Long membershipId) {
            this.membershipId = membershipId;
            return this;
        }

        public CheckRegisteredBankAccountCommand.CheckRegisteredBankAccountCommandBuilder checkRegisteredBankAccountAssociationId(String checkRegisteredBankAccountAssociationId) {
            this.checkRegisteredBankAccountAssociationId = checkRegisteredBankAccountAssociationId;
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
            return new CheckRegisteredBankAccountCommand(aggregateId, rechargingRequestAssociationId, membershipId, checkRegisteredBankAccountAssociationId, bankName, bankAccountNumber, amount);
        }

    }

    @Override
    public String toString() {
        return "CheckRegisteredBankAccountCommand{" +
                "aggregateId='" + aggregateId + '\'' +
                ", rechargingRequestId=" + rechargingRequestAssociationId +
                ", MembershipId=" + membershipId +
                ", checkRegisteredBankAccountId='" + checkRegisteredBankAccountAssociationId + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", amount=" + amount +
                '}';
    }
}
