package org.opennuri.study.architecture.money.application.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class FirmBankingResponse {
    private String membershipId;
    private Long moneyAmount;
    private FirmBankingResultCode resultCode;
    private String resultMessage;


    @Getter
    @AllArgsConstructor
    public enum FirmBankingResultCode {
        SUCCESS("성공"),
        FAIL("실패");

        private final String description;
    }

    @Override
    public String toString() {
        return "FirmBankingResponse{" +
                "membershipId='" + membershipId + '\'' +
                ", moneyAmount=" + moneyAmount +
                ", resultCode=" + resultCode +
                ", resultMessage='" + resultMessage + '\'' +
                '}';
    }
}
