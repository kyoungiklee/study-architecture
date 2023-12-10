package org.opennuri.study.architecture.banking.appication.port.out;

import org.opennuri.study.architecture.banking.adapter.out.external.bank.ExternalFirmBankingRequest;
import org.opennuri.study.architecture.banking.adapter.out.external.bank.FirmBankingResult;

public interface RequestExternalFirmBankingPort {
    FirmBankingResult requestExternalFirmBanking(ExternalFirmBankingRequest request);

}
