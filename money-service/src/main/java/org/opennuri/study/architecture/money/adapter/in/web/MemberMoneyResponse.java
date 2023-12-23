package org.opennuri.study.architecture.money.adapter.in.web;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class MemberMoneyResponse {
    private String membershipId;

    private String moneyAmount;
    private String message;

    public MemberMoneyResponse(Long membershipId, Long moneyAmount, String message) {
        this.membershipId = String.valueOf(membershipId);
        this.moneyAmount = String.valueOf(moneyAmount);
        this.message = message;
    }

    public void setMembershipId(Long membershipId) {

        if(membershipId != null) {
            this.membershipId = String.valueOf(membershipId);
        } else {
            this.membershipId = "";
        }
    }

    public void setMoneyAmount(Long moneyAmount) {

        if(moneyAmount != null) {
            this.moneyAmount = String.valueOf(moneyAmount);
        } else {
            this.moneyAmount = "";
        }
    }

    public void setMessage(String message) {
        this.message = Objects.requireNonNullElse(message, "");
    }
}
