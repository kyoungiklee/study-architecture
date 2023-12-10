package org.opennuri.study.architecture.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisteredBankAccount {

    private final String registeredBankAccountId;
    private final String membershipId;
    private final String bankName;
    private final String bankAccountNumber;
    private final boolean validLinkedStatus;

    public static RegisteredBankAccount generateRegisteredBankAccount (
            RegisteredBankAccount.RegisteredBankAccountId registeredBankAccountId,
            RegisteredBankAccount.MembershipId membershipId,
            RegisteredBankAccount.BankName bankName,
            RegisteredBankAccount.BankAccountNumber bankAccountNumber,
            RegisteredBankAccount.ValidLinkedStatus validlinkedStatus
    ) {
        return new RegisteredBankAccount(
                registeredBankAccountId.registeredBankAccountIdValue,
                membershipId.membershipIdValue,
                bankName.bankNameValue,
                bankAccountNumber.bankAccountNumberValue,
                validlinkedStatus.validLinkedStatusValue
        );
    }

    @Value
    public static class RegisteredBankAccountId {
        public RegisteredBankAccountId(String value) {
            this.registeredBankAccountIdValue = value;
        }
        String registeredBankAccountIdValue;
    }

    @Value
    public static class MembershipId {
        public MembershipId(String value) {
            this.membershipIdValue = value;
        }
        String membershipIdValue;
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
}
