package org.opennuri.study.architecture.banking.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FindBankAccountResponse {
    private String membershipId;
    private String bankName;
    private String bankAccountNumber;
    private boolean validLinkedStatus;
}
