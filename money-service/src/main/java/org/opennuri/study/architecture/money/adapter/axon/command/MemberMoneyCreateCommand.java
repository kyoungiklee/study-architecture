package org.opennuri.study.architecture.money.adapter.axon.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.opennuri.study.architecture.common.SelfValidating;

@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class MemberMoneyCreateCommand extends SelfValidating<MemberMoneyCreateCommand> {

    @NotNull
    private Long membershipId;
    @NotNull @PositiveOrZero
    private Long balance;

    public MemberMoneyCreateCommand(Long membershipId, Long balance) {
        this.membershipId = membershipId;
        this.balance = balance;
        this.validateSelf();
    }
}
