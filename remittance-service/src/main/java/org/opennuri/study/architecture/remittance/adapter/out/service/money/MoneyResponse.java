package org.opennuri.study.architecture.remittance.adapter.out.service.money;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MoneyResponse {
    private Long membershipId;
    private Long amount;
    private String status;

    public MoneyResponse(Long membershipId, Long amount, String status) {
        this.membershipId = membershipId;
        this.amount = amount;
        this.status = status;
    }

    @Override
    public String toString() {
        return "MoneyResponse{" +
                "membershipId=" + membershipId +
                ", amount=" + amount +
                ", success='" + status + '\'' +
                '}';
    }
}
