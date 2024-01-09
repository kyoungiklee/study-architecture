package org.opennuri.study.architecture.common.axon.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RollbackFirmbankingFinishedEvent {
    private String rollbackFirmBankingAggregateId; // 충전요청 ID
}
