package org.opennuri.study.architecture.remittance.application.port.out.money;

import org.opennuri.study.architecture.remittance.adapter.out.service.money.IncreaseMoneyResponse;

public interface MoneyServicePort {
    MoneyInfo getMoneyInfo(Long membershipId);
    boolean requestMoneyRecharge(String membershipId, int amount);
    IncreaseMoneyResponse requestMoneyIncrease(Long membershipId, Long amount);
    boolean requestMoneyDecrease(String membershipId, int amount);

}
