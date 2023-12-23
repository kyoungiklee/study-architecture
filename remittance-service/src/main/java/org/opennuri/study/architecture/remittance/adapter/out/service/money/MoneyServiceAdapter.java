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
    public boolean requestMoneyRecharge(String membershipId, int amount) {
        return false;
    }

    @Override
    public IncreaseMoneyResponse requestMoneyIncrease(Long membershipId, Long amount) {
        //membershipId 값이 number형인지 확인한다.
        String stringOfMembershipId;
        try {
            stringOfMembershipId = String.valueOf(membershipId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("membershipId is not number");
        }
        //amount 값이 number형인지 확인한다.
        String stringOfAmount;
        try {
            stringOfAmount = String.valueOf(amount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("amount is not number");
        }

        //IncreaseMoneyRequest 객체를 생성한다.
        IncreaseMoneyRequest increaseMoneyRequest = new IncreaseMoneyRequest(membershipId, amount);
        String url = String.join("/", moneyUrl, "money/increase");

        //restTemplate을 사용하여 money 서비스에 요청한다.(post)
        IncreaseMoneyResponse increaseMoneyResponse =
                restTemplate.postForObject(url, increaseMoneyRequest, IncreaseMoneyResponse.class);
        log.info("increaseMoneyResponse: {}", increaseMoneyResponse);
        return increaseMoneyResponse;
    }

    @Override
    public boolean requestMoneyDecrease(String membershipId, int amount) {
        return false;
    }
}
