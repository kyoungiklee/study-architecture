package org.opennuri.study.architecture.money.adapter.axon.command;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.opennuri.study.architecture.common.SelfValidating;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberMoneyIncreaseCommand extends SelfValidating<MemberMoneyIncreaseCommand> {
    @NotNull
    @TargetAggregateIdentifier
    private String aggregateId;
    @NotNull
    private Long membershipId;
    @NotNull
    private Long amount;
}
