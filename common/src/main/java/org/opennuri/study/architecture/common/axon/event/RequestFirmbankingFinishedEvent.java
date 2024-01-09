package org.opennuri.study.architecture.common.axon.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestFirmbankingFinishedEvent {
    private String requestFirmBankingAggregateId; // 충전요청 Aggregate ID
    private String requestFirmBankingId; // 충전요청 ID
    private String rechargingRequestId; // 충전요청 ID
    private String aggregateId; // 충전요청 Aggregate ID
    private String membershipId; // 충전요청 회원 ID
    private String fromBankName; // 출금 은행명
    private String fromBankAccountNumber; // 출금 계좌번호
    private String toBankName; // 입금 은행명
    private String toBankAccountNumber; // 입금 계좌번호
    private Long moneyAmount; // 출금 금액
    private String description; // 출금 설명
    private boolean isFinished; // 충전요청 완료 여부 0: 미완료, 1: 완료
}
