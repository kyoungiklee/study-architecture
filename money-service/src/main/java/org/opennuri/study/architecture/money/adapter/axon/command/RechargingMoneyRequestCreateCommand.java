package org.opennuri.study.architecture.money.adapter.axon.command;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.opennuri.study.architecture.common.SelfValidating;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Builder
public class RechargingMoneyRequestCreateCommand extends SelfValidating<RechargingMoneyRequestCreateCommand> {
    @TargetAggregateIdentifier
    private String aggregateId; //MemberMoneyAggregate ID
    @NotNull
    private String rechargingRequestAssociationId; // 충전요청 command <--> event 연관관계 ID
    @NotNull
    private Long membershipId; //회원 ID
    @NotNull
    private Long amount; // 충전 요청 금액

    public RechargingMoneyRequestCreateCommand(String aggregateId, String rechargingRequestAssociationId, Long membershipId, Long amount) {
        this.aggregateId = aggregateId;
        this.rechargingRequestAssociationId = rechargingRequestAssociationId;
        this.membershipId = membershipId;
        this.amount = amount;
        this.validateSelf();
    }
}
