package org.opennuri.study.architecture.banking.appication.port.in;

import org.opennuri.study.architecture.banking.domain.RegisteredBankAccount;

public interface RegisterBankAccountUseCase {

    RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command);
}
