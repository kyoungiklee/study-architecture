package org.opennuri.study.architecture.remittance.adapter.out.service.money;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class IncreaseMoneyResponse {
    private Long membershipId;
    private Long amount;
    private String status;

    public IncreaseMoneyResponse(Long membershipId, Long amount, String status) {
        this.membershipId = membershipId;
        this.amount = amount;
        this.status = status;
    }

    @Override
    public String toString() {
        return "IncreaseMoneyResponse{" +
                "membershipId=" + membershipId +
                ", amount=" + amount +
                ", success='" + status + '\'' +
                '}';
    }
}
