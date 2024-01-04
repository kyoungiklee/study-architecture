package org.opennuri.study.architecture.money.adapter.axon.event;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.opennuri.study.architecture.common.SelfValidating;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class MemberMoneyCreateEvent extends SelfValidating<MemberMoneyCreateEvent> {
    @NotNull
    private Long membershipId;
    @NotNull @PositiveOrZero
    private Long balance;

    public MemberMoneyCreateEvent(@NotNull Long membershipId, @NotNull @PositiveOrZero Long balance) {
        this.membershipId = membershipId;
        this.balance = balance;
        this.validateSelf();
    }
}
