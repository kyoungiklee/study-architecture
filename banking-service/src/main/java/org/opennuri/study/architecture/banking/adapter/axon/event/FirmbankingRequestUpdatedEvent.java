package org.opennuri.study.architecture.banking.adapter.axon.event;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.opennuri.study.architecture.banking.adapter.out.persistence.FirmBankingRequestStatus;

@Data
@NoArgsConstructor
@Builder
public class FirmbankingRequestUpdatedEvent {

    private String aggregateId;
    private FirmBankingRequestStatus status;
    public FirmbankingRequestUpdatedEvent(@NotNull String aggregateId, FirmBankingRequestStatus status) {
        this.aggregateId = aggregateId;
        this.status = status;
    }
}
