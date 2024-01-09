package org.opennuri.study.architecture.banking.adapter.axon.command;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.opennuri.study.architecture.banking.adapter.out.persistence.FirmBankingRequestStatus;
import org.opennuri.study.architecture.common.SelfValidating;

@NoArgsConstructor
@Data
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

    public static UpdateFirmBankingRequestCommandBuilder builder() {
        return new UpdateFirmBankingRequestCommandBuilder();
    }

    // Builder pattern implementation
    public static class UpdateFirmBankingRequestCommandBuilder {
        private String aggregateId;
        private FirmBankingRequestStatus status;

        public UpdateFirmBankingRequestCommandBuilder aggregateId(String aggregateId) {
            this.aggregateId = aggregateId;
            return this;
        }

        public UpdateFirmBankingRequestCommandBuilder status(FirmBankingRequestStatus status) {
            this.status = status;
            return this;
        }

        public UpdateFirmBankingRequestCommand build() {
            return new UpdateFirmBankingRequestCommand(aggregateId, status);
        }
    }
}
