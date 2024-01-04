package org.opennuri.study.architecture.money.adapter.axon.event;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.opennuri.study.architecture.common.SelfValidating;


@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class MemberMoneyIncreaseEvent extends SelfValidating<MemberMoneyIncreaseEvent> {

    @NotNull
    private String aggregateId;
    @NotNull
    private Long membershipId;
    @NotNull
    private Long amount;

    public MemberMoneyIncreaseEvent(@NotNull String aggregateId, @NotNull Long membershipId, @NotNull Long amount) {
        this.aggregateId = aggregateId;
        this.membershipId = membershipId;
        this.amount = amount;
        this.validateSelf();
    }
}
