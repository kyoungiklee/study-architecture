package org.opennuri.study.architecture.banking.appication.port.in;

import org.opennuri.study.architecture.banking.adapter.out.external.bank.FirmBankingResult;

public interface RequestFirmBankingUseCase {

    FirmBankingResult requestFirmBanking(RequestFirmBankingCommand command);
}
