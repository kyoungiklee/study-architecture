package org.opennuri.study.architecture.banking.adapter.out.persistence;

import org.opennuri.study.architecture.banking.domain.RegisteredBankAccount;
import org.springframework.stereotype.Component;

@Component
public class RegisteredBankAccountMapper {

    public RegisteredBankAccount mapToDomainEntity(RegisteredBankAccountJpaEntity registeredBankAccountJpaEntity) {
        return RegisteredBankAccount.generateRegisteredBankAccount(
                new RegisteredBankAccount.RegisteredBankAccountId(registeredBankAccountJpaEntity.getRegisteredBankAccountId()+""),
                new RegisteredBankAccount.MembershipId(registeredBankAccountJpaEntity.getMembershipId()),
                new RegisteredBankAccount.BankName(registeredBankAccountJpaEntity.getBankName()),
                new RegisteredBankAccount.BankAccountNumber(registeredBankAccountJpaEntity.getBankAccountNumber()),
                new RegisteredBankAccount.ValidLinkedStatus(registeredBankAccountJpaEntity.isValidLinkedStatus())
        );
    }
}
