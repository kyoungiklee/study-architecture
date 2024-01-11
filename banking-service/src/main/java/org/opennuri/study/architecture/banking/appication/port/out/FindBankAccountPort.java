package org.opennuri.study.architecture.banking.appication.port.out;

import org.opennuri.study.architecture.banking.domain.RegisteredBankAccount;

public interface FindBankAccountPort {
    RegisteredBankAccount findBankAccount(Long membershipId);

    RegisteredBankAccount findBankAccount(String bankName, String bankAccountNumber);
}
