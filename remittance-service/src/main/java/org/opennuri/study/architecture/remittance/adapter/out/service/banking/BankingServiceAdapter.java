package org.opennuri.study.architecture.remittance.adapter.out.service.banking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.common.ServiceAdapter;
import org.opennuri.study.architecture.remittance.application.port.out.banking.BankingInfo;
import org.opennuri.study.architecture.remittance.application.port.out.banking.BankingServicePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@Slf4j
@ServiceAdapter
@RequiredArgsConstructor
public class BankingServiceAdapter implements BankingServicePort {
    private final RestTemplate restTemplate;

    @Value("${service.banking.url}")
    private String bankingServiceUrl;

    @Override
    public BankingInfo getMembershipBankingInfo(String membershipId) {
        //banking service를 통해 membershipId에 해당하는 banking 정보를 조회한다.
        String url = String.join("/", bankingServiceUrl, "banking/account", membershipId);
        log.info("url: {}", url);

        BankingInfo bankingInfo = restTemplate.getForObject(url, BankingInfo.class);
        log.info("bankingInfo: {}", bankingInfo);

        if (bankingInfo != null) {
            return bankingInfo;
        } else {
            throw new RuntimeException("bankingInfo is null");
        }
    }

    @Override
    public boolean requestFirmBanking(FirmBankingRequest firmBankingRequest) {

        //banking service를 통해 firmbanking을 post로 요청한다.
        String url = String.join("/", bankingServiceUrl, "banking/firmbanking/request");

        FirmBankingResponse firmBankingResponse =
                restTemplate.postForObject(url, firmBankingRequest, FirmBankingResponse.class);

        log.info("firmBankingResponse: {}", firmBankingResponse);

        return firmBankingResponse != null && firmBankingResponse.getResultCode().equals("SUCCESS");
    }
}
