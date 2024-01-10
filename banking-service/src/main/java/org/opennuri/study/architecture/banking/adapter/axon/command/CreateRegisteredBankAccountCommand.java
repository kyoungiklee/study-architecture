package org.opennuri.study.architecture.banking.adapter.axon.command;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.opennuri.study.architecture.common.SelfValidating;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CreateRegisteredBankAccountCommand extends SelfValidating<CreateRegisteredBankAccountCommand> {
    private Long membershipId;
    private String bankName;
    private String bankAccountNumber;

    public CreateRegisteredBankAccountCommand(Long membershipId, String bankName, String bankAccountNumber) {
        this.membershipId = membershipId;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.validateSelf();
    }
}
