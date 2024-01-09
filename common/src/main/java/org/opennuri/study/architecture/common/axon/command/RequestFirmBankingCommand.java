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
public class RequestFirmBankingCommand {

    private String requestFirmBankingId; // 충전요청 ID
    @TargetAggregateIdentifier
    private String aggregateId; // 충전요청 Aggregate ID
    private String membershipId; // 충전요청 회원 ID
    private String fromBankName; // 출금 은행명
    private String fromBankAccountNumber; // 출금 계좌번호
    private String toBankName; // 입금 은행명
    private String toBankAccountNumber; // 입금 계좌번호
    private Long moneyAmount; // 출금 금액
    private String description; // 출금 설명
}
