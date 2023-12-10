package org.opennuri.study.architecture.banking.adapter.out.external.bank;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankAccount {

    private String bankName;
    private String bankAccountNUmber;
    private boolean isValid;

    public BankAccount(String bankName, String bankAccountNUmber, boolean isValid) {
        this.bankName = bankName;
        this.bankAccountNUmber = bankAccountNUmber;
        this.isValid = isValid;
    }
}
