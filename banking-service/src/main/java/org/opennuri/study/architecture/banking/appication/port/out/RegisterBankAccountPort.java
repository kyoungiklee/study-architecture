package org.opennuri.study.architecture.banking.appication.port.out;

import org.opennuri.study.architecture.banking.domain.RegisteredBankAccount;

public interface RegisterBankAccountPort {

     RegisteredBankAccount createRegisteredBankAccount(
            RegisteredBankAccount.MembershipId membershipId
            , RegisteredBankAccount.BankName bankName
            , RegisteredBankAccount.BankAccountNumber bankAccountNumber
            , RegisteredBankAccount.ValidLinkedStatus validLinkedStatus
            , RegisteredBankAccount.AggregateId aggregateId
    );
}
