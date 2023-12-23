package org.opennuri.study.architecture.money.application.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BankingAccountResponse {
    private String membershipId;
    private String bankName;
    private String bankAccountNumber;
    private boolean validLinkedStatus;
}
