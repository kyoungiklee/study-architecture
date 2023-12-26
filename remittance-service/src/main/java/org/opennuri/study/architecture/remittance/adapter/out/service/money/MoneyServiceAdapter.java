package org.opennuri.study.architecture.remittance.adapter.out.service.money;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.common.ServiceAdapter;
import org.opennuri.study.architecture.remittance.application.port.out.money.MoneyInfo;
import org.opennuri.study.architecture.remittance.application.port.out.money.MoneyServicePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@Slf4j
@ServiceAdapter
@RequiredArgsConstructor
public class MoneyServiceAdapter implements MoneyServicePort {
    private final RestTemplate restTemplate;

    @Value("${service.money.url}")
    private String moneyUrl;
    @Override
    public MoneyInfo getMoneyInfo(Long membershipId) {
        //membershipId 값이 number형인지 확인한다.
        String stringOfMembershipId;
        try {
            stringOfMembershipId = String.valueOf(membershipId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("membershipId is not number");
        }

        String url = String.join("/", moneyUrl, "money/find", stringOfMembershipId);
        log.info("url: {}", url);

        return restTemplate.getForObject(url, MoneyInfo.class);
    }

    @Override
    public MoneyResponse requestMoneyIncrease(Long membershipId, Long amount) {


        //MoneyRequest 객체를 생성한다.
        MoneyRequest increaseMoneyRequest = new MoneyRequest(membershipId, amount);
        String url = String.join("/", moneyUrl, "money/increase");

        MoneyResponse increaseMoneyResponse;
        try {
            //restTemplate을 사용하여 money 서비스에 요청한다.(post)
            increaseMoneyResponse =
                    restTemplate.postForObject(url, increaseMoneyRequest, MoneyResponse.class);
            log.info("increaseMoneyResponse: {}", increaseMoneyResponse);
        } catch (Exception e) {
            throw new RuntimeException(String.format("remittance-service -> money-service 통신 실패: %s", e.getMessage()));
        }


        if(increaseMoneyResponse != null && increaseMoneyResponse.getStatus().equals("SUCCESS")) {
            return increaseMoneyResponse;
        } else {
            throw new RuntimeException("increaseMoneyResponse is null");
        }
    }

    @Override
    public MoneyResponse requestMoneyDecrease(Long membershipId, Long amount) {
        MoneyRequest decreaseMoneyRequest = new MoneyRequest(membershipId, amount);
        String url = String.join("/", moneyUrl, "money/decrease");
        MoneyResponse decreaseMoneyResponse = restTemplate.postForObject(url, decreaseMoneyRequest, MoneyResponse.class);

        if(decreaseMoneyResponse != null && decreaseMoneyResponse.getStatus().equals("SUCCESS")) {
            return decreaseMoneyResponse;
        } else {
            throw new RuntimeException("moneyResponse is null");
        }
    }

    @Override
    public MoneyResponse requestMoneyRecharging(Long membershipId, Long rechargingAmount) {
        return null;
    }

}
