package org.opennuri.study.architecture.remittance.application.port.out.banking;

import org.opennuri.study.architecture.remittance.adapter.out.service.banking.FirmBankingRequest;

public interface BankingServicePort {
    BankingInfo getMembershipBankingInfo(String membershipId);
    boolean requestFirmBanking(FirmBankingRequest firmBankingRequest);
}
