package org.opennuri.study.architecture.banking.appication.port.in;

import org.opennuri.study.architecture.banking.adapter.out.external.bank.FirmBankingResult;

import java.util.concurrent.ExecutionException;

public interface RequestFirmBankingUseCase {

    FirmBankingResult requestFirmBanking(RequestFirmBankingCommand command);

    FirmBankingResult requestFirmBankingByEvent(RequestFirmBankingCommand build) throws ExecutionException, InterruptedException;
}
