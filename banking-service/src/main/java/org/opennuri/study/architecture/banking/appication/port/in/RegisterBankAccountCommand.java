package org.opennuri.study.architecture.banking.appication.port.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.opennuri.study.architecture.common.SelfValidating;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class RegisterBankAccountCommand extends SelfValidating<RegisterBankAccountCommand> {

    @NotNull
    private final Long membershipId;

    @NotNull
    @NotBlank
    private final String bankName;

    @NotNull
    @NotBlank
    private final String bankAccountNumber;

    private final boolean validLinkedStatus;

    public RegisterBankAccountCommand(Long membershipId, String bankName, String bankAccountNumber, boolean validLinkedStatus) {
        this.membershipId = membershipId;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.validLinkedStatus = validLinkedStatus;

        this.validateSelf();
    }

}
