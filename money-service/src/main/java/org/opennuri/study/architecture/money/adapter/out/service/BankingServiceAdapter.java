package org.opennuri.study.architecture.money.adapter.out.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.common.ServiceAdapter;
import org.opennuri.study.architecture.money.application.port.out.banking.FindBankAccountPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@Slf4j
@ServiceAdapter
@RequiredArgsConstructor
public class BankingServiceAdapter implements FindBankAccountPort {
    private final RestTemplate restTemplate;

    @Value("${service.banking.url}")
    private String bankingServiceUrl;
    @Override
    public BankAccount findBankAccountByMembershipId(Long membershipId) {
        String url = String.join("/", bankingServiceUrl, "banking/account", membershipId.toString());
        BankAccount bankAccount = restTemplate.getForObject(url, BankAccount.class);

        if (bankAccount == null) {
            throw new IllegalArgumentException("bankAccount is null");
        }
        return bankAccount;
    }
}
