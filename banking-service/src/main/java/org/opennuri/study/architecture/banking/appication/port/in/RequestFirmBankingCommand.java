package org.opennuri.study.architecture.banking.appication.port.in;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.opennuri.study.architecture.common.SelfValidating;


@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class RequestFirmBankingCommand extends SelfValidating<RequestFirmBankingCommand> {

    @NotNull
    private final Long membershipId;
    @NotBlank
    private final String fromBankName;
    @NotBlank
    private final String fromBankAccountNumber;
    @NotBlank
    private final String toBankName;
    @NotBlank
    private final String toBankAccountNumber;
    @Positive
    private final Long moneyAmount;
    private final String requestStatus;
    private final String description;

    public RequestFirmBankingCommand(
            Long membershipId
            , String fromBankName
            , String fromBankAccountNumber
            , String toBankName
            , String toBankAccountNumber
            , Long moneyAmount
            , String requestStatus
            , String description) {
        this.membershipId = membershipId;
        this.fromBankName = fromBankName;
        this.fromBankAccountNumber = fromBankAccountNumber;
        this.toBankName = toBankName;
        this.toBankAccountNumber = toBankAccountNumber;
        this.moneyAmount = moneyAmount;
        this.requestStatus = requestStatus;
        this.description = description;

        this.validateSelf();
    }

}
