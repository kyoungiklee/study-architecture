package org.opennuri.study.architecture.money.adapter.in.web;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberMoneyResponse {
    private Long membershipId;
    private Long balance;
    private boolean valid;
    private String message;

    @Override
    public String toString() {
        return "MemberMoneyResponse{" +
                "membershipId=" + membershipId +
                ", moneyAmount=" + balance +
                ", isValid=" + valid +
                ", message='" + message + '\'' +
                '}';
    }
}
