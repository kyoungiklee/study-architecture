package org.opennuri.study.architecture.remittance.application.port.out.money;

import org.opennuri.study.architecture.remittance.adapter.out.service.money.MoneyResponse;

public interface MoneyServicePort {
    MoneyInfo getMoneyInfo(Long membershipId);

    MoneyResponse requestMoneyIncrease(Long membershipId, Long amount);
    MoneyResponse requestMoneyDecrease(Long membershipId, Long amount);
    MoneyResponse requestMoneyRecharging(Long membershipId, Long rechargingAmount);
}
