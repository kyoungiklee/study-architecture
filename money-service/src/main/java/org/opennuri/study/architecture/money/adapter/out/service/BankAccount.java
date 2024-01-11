package org.opennuri.study.architecture.money.adapter.out.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    private String membershipId;
    private String bankName;
    private String bankAccountNumber;
    private boolean validLinkedStatus;
    private String aggregateId;
}
