package org.opennuri.study.architecture.remittance.adapter.out.service.money;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MoneyResponse {
    private Long membershipId;
    private Long amount;
    private String status;

    @Override
    public String toString() {
        return "MoneyResponse{" +
                "membershipId=" + membershipId +
                ", amount=" + amount +
                ", success='" + status + '\'' +
                '}';
    }
}
