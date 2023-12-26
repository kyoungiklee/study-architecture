package org.opennuri.study.architecture.remittance.adapter.out.service.money;


import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class MoneyRequest {
    private Long membershipId;
    private Long moneyAmount;

    public MoneyRequest(Long membershipId, Long moneyAmount) {
        this.membershipId = membershipId;
        this.moneyAmount = moneyAmount;
    }

    public void setMembershipId(Long membershipId) {
        this.membershipId = membershipId;
    }

    public void setAmount(Long moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
}
