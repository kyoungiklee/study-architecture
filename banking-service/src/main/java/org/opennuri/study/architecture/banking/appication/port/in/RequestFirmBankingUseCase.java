package org.opennuri.study.architecture.banking.appication.port.in;

import org.opennuri.study.architecture.banking.domain.FirmBankingRequest;

public interface RequestFirmBankingUseCase {

    FirmBankingRequest requestFirmBanking(RequestFirmBankingCommand command);
}
