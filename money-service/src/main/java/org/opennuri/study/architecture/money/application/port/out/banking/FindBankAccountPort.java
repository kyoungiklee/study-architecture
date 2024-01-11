package org.opennuri.study.architecture.money.application.port.out.banking;

import org.opennuri.study.architecture.money.adapter.out.service.BankAccount;

public interface FindBankAccountPort {
    BankAccount findBankAccountByMembershipId(Long membershipId);
}
