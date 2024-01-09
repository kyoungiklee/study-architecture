package org.opennuri.study.architecture.banking.adapter.axon.command;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.checkerframework.checker.units.qual.N;
import org.opennuri.study.architecture.banking.adapter.out.persistence.FirmBankingRequestStatus;
import org.opennuri.study.architecture.common.SelfValidating;

@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class UpdateFirmBankingRequestCommand extends SelfValidating<UpdateFirmBankingRequestCommand> {
    @NotNull
    @TargetAggregateIdentifier
    private String aggregateId;
    private FirmBankingRequestStatus status;

    public UpdateFirmBankingRequestCommand(String aggregateId, FirmBankingRequestStatus status) {
        this.aggregateId = aggregateId;
        this.status = status;
        this.validateSelf();
    }
}
