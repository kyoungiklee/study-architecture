package org.opennuri.study.architecture.banking.appication.port.in;


import org.opennuri.study.architecture.banking.adapter.in.web.UpdateFirmBankingResponse;

public interface UpdateFirmBankingUseCase {

    UpdateFirmBankingResponse updateFirmbankingByEvent( UpdateFirmBankingCommand command);
}
