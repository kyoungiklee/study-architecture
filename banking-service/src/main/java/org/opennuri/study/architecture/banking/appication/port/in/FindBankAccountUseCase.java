package org.opennuri.study.architecture.banking.appication.port.in;

import org.opennuri.study.architecture.banking.domain.RegisteredBankAccount;

import java.util.Optional;

public interface FindBankAccountUseCase {
    Optional<RegisteredBankAccount> findBankAccount(Long membershipId);
}
