package org.opennuri.study.architecture.banking.adapter.out.external.bank;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetBankAccountInfoRequest {
    private String bankName;
    private String bankAccountNumber;

    public GetBankAccountInfoRequest(String bankName, String bankAccountNumber) {
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
    }
}
