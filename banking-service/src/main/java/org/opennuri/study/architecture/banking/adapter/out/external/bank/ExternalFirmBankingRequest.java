package org.opennuri.study.architecture.banking.adapter.out.external.bank;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ExternalFirmBankingRequest {
    private String fromBankName;
    private String fromBankAccountNumber;
    private String toBankName;
    private String toBankAccountNumber;
    private Long moneyAmount;
}
