package org.opennuri.study.architecture.remittance.adapter.out.service.banking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FirmBankingResponse {
    private Long membershipId;
    private Long moneyAmount;
    private String resultCode;
    private String resultMessage;

    @Override
    public String toString() {
        return "FirmBankingResponse{" +
                "membershipId=" + membershipId +
                ", moneyAmount=" + moneyAmount +
                ", resultCode='" + resultCode + '\'' +
                ", resultMessage='" + resultMessage + '\'' +
                '}';
    }
}
