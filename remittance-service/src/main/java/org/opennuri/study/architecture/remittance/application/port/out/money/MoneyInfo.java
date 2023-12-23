package org.opennuri.study.architecture.remittance.application.port.out.money;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor

public class MoneyInfo {
    private String membershipId;
    private String moneyAmount;
    private String message;

    @Override
    public String toString() {
        return "MoneyInfo{" +
                "membershipId='" + membershipId + '\'' +
                ", moneyAmount='" + moneyAmount + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
