package org.opennuri.study.architecture.banking.adapter.axon.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FirmbankingRequestCreatedEvent {
    private String toBankName;
    private String toBankAccountNumber;

    private String fromBankName;
    private String fromBankAccountNumber;

    private Long amount;

    @Override
    public String toString() {
        return "FirmbankingRequestCreatedEvent{" +
                "toBankName='" + toBankName + '\'' +
                ", toBankAccountNumber='" + toBankAccountNumber + '\'' +
                ", fromBankName='" + fromBankName + '\'' +
                ", fromBankAccountNumber='" + fromBankAccountNumber + '\'' +
                ", amount=" + amount +
                '}';
    }
}
