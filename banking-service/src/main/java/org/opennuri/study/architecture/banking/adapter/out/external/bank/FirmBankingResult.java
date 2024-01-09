package org.opennuri.study.architecture.banking.adapter.out.external.bank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;


@Builder
@Data
@AllArgsConstructor
public class FirmBankingResult {
    private Long membershipId;
    private String fromBankName;
    private String fromBankAccountNumber;
    private String toBankName;
    private String toBankAccountNumber;
    private Long moneyAmount;
    private String aggregateId;
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
                "membershipId=" + membershipId +
                ", fromBankName='" + fromBankName + '\'' +
                ", fromBankAccountNumber='" + fromBankAccountNumber + '\'' +
                ", toBankName='" + toBankName + '\'' +
                ", toBankAccountNumber='" + toBankAccountNumber + '\'' +
                ", moneyAmount=" + moneyAmount +
                ", aggregateId='" + aggregateId + '\'' +
                ", resultCode=" + resultCode +
                ", resultMessage='" + resultMessage + '\'' +
                '}';
    }
}
