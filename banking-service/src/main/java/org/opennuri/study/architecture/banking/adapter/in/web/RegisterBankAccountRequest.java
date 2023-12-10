package org.opennuri.study.architecture.banking.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterBankAccountRequest {
    private String membershipId;
    private String bankName;
    private String bankAccountNumber;
    private boolean validLinkedStatus;

}
