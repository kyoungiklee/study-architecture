package org.opennuri.study.architecture.banking.appication.port.out;

import org.opennuri.study.architecture.banking.adapter.out.external.bank.BankAccount;
import org.opennuri.study.architecture.banking.adapter.out.external.bank.GetBankAccountInfoRequest;

public interface RequestBankAccountInfoPort {

    BankAccount getBankAccountInfo(GetBankAccountInfoRequest getBankAccountInfoRequest);
}
