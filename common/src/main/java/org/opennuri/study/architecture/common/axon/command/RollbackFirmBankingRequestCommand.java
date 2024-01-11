package org.opennuri.study.architecture.common.axon.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RollbackFirmBankingRequestCommand {
    @TargetAggregateIdentifier
    private String aggregateId; // 롤백 대상 충전요청 Aggregate ID
    private String rollbackFirmBankingRequestId; // 롤백 대상 충전요청 ID
    private String rechargingRequestId; // 롤백 대상 충전요청 ID

    //business properties
    private Long membershipId; // 롤백 대상 충전요청 회원 ID
    private String fromBankName; // 롤백 대상 충전요청 출금 은행명(법인계좌)
    private String fromBankAccountNumber; // 롤백 대상 충전요청 출금 계좌번호(법인계좌)
    private String toBankName; // 롤백 대상 충전요청 입금 은행명 (고객계좌)
    private String toBankAccountNumber; // 롤백 대상 충전요청 입금 계좌번호(고객계좌)
    private Long amount; // 롤백 대상 충전요청 금액
    private String description; // 롤백 대상 충전요청 설명
}
