package org.opennuri.study.architecture.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisteredBankAccount {

    private final Long registeredBankAccountId;
    private final Long membershipId;
    private final String bankName;
    private final String bankAccountNumber;
    private final boolean validLinkedStatus;
    private final String aggregateId;

    public static RegisteredBankAccount generateRegisteredBankAccount (
            RegisteredBankAccount.RegisteredBankAccountId registeredBankAccountId,
            RegisteredBankAccount.MembershipId membershipId,
            RegisteredBankAccount.BankName bankName,
            RegisteredBankAccount.BankAccountNumber bankAccountNumber,
            RegisteredBankAccount.ValidLinkedStatus validlinkedStatus,
            RegisteredBankAccount.AggregateId aggregateId
    ) {
        return new RegisteredBankAccount(
                registeredBankAccountId.registeredBankAccountIdValue,
                membershipId.membershipIdValue,
                bankName.bankNameValue,
                bankAccountNumber.bankAccountNumberValue,
                validlinkedStatus.validLinkedStatusValue
                , aggregateId.aggregateIdValue
        );
    }

    @Value
    public static class RegisteredBankAccountId {
        public RegisteredBankAccountId(Long value) {
            this.registeredBankAccountIdValue = value;
        }
        Long registeredBankAccountIdValue;
    }

    @Value
    public static class MembershipId {
        public MembershipId(Long value) {
            this.membershipIdValue = value;
        }
        Long membershipIdValue;
    }
    @Value
    public static class BankName {
        public BankName(String value) {
            this.bankNameValue = value;
        }
        String bankNameValue;
    }

    @Value
    public static class BankAccountNumber {
        public BankAccountNumber(String value) {
            this.bankAccountNumberValue = value;
        }
        String bankAccountNumberValue;
    }

    @Value
    public static class ValidLinkedStatus {
        public ValidLinkedStatus(boolean value) {
            this.validLinkedStatusValue = value;
        }
        boolean validLinkedStatusValue;
    }

    @Value
    public static class AggregateId {
        public AggregateId(String value) {
            this.aggregateIdValue = value;
        }
        String aggregateIdValue;
    }
}
