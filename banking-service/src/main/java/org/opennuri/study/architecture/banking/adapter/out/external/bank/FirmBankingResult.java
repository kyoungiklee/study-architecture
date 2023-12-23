package org.opennuri.study.architecture.banking.adapter.out.external.bank;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FirmBankingResult {
    private Long membershipId;
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
        return "FirmBankingResult{" +
                "membershipId='" + membershipId + '\'' +
                ", moneyAmount=" + moneyAmount +
                ", resultCode=" + resultCode +
                ", resultMessage='" + resultMessage + '\'' +
                '}';
    }
}
