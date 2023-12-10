package org.opennuri.study.architecture.banking.adapter.out.external.bank;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FirmBankingResult {
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
                "resultCode=" + resultCode +
                ", resultMessage='" + resultMessage + '\'' +
                '}';
    }
}
