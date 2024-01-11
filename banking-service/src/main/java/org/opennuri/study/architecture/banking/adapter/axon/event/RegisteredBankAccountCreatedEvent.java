package org.opennuri.study.architecture.banking.adapter.axon.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisteredBankAccountCreatedEvent {
    private Long membershipId;
    private String bankName;
    private String bankAccountNumber;
}
