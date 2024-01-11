package org.opennuri.study.architecture.common.axon.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RollbackFirmbankingFinishedEvent {
    private String rollbackFirmBankingAggregateId; // 충전요청 ID
    private Long membershipId; // 충전요청 회원 ID
}
