package org.opennuri.study.architecture.common.axon.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckedRegisteredBankAccountEvent {
    private String rechargingRequestId; // 충전 요청 ID
    private String checkRegisteredBankAccountId; // 충전요청 등록된 계좌 확인 ID
    private String membershipId; //충전요청 회원 ID
    private String bankName; // 충전요청 은행명
    private String bankAccountNumber; // 충전요청 계좌번호
    private Long amount; // 충전요청 금액
    private String firmBankingRequestAggregateId; // 충전요청 Aggregate ID
    private boolean isChecked; // 충전요청 등록된 계좌 확인 여부
}
