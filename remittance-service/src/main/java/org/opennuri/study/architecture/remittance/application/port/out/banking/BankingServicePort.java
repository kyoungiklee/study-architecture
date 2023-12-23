package org.opennuri.study.architecture.remittance.application.port.out.banking;

public interface BankingServicePort {
    BankingInfo getMembershipBankingInfo(String membershipId);
    boolean requestFirmBanking(String bankName, String accountNumber, int amount);
}
