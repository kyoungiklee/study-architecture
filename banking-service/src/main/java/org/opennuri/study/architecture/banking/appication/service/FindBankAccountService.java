package org.opennuri.study.architecture.banking.appication.service;

import lombok.RequiredArgsConstructor;
import org.opennuri.study.architecture.banking.appication.port.in.FindBankAccountUseCase;
import org.opennuri.study.architecture.banking.appication.port.out.FindBankAccountPort;
import org.opennuri.study.architecture.banking.domain.RegisteredBankAccount;
import org.opennuri.study.architecture.common.UseCase;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class FindBankAccountService implements FindBankAccountUseCase {
    private final FindBankAccountPort findBankAccountPort;
    @Override
    public Optional<RegisteredBankAccount> findBankAccount(Long membershipId) {
        RegisteredBankAccount bankAccount = findBankAccountPort.findBankAccount(membershipId);
        return Optional.of(bankAccount);
    }
}
