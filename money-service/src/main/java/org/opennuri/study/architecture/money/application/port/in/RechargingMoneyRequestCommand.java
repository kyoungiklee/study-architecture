package org.opennuri.study.architecture.money.application.port.in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.opennuri.study.architecture.common.SelfValidating;


@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class RechargingMoneyRequestCommand  extends SelfValidating<RechargingMoneyRequestCommand> {

    @NotNull
    private Long membershipId;
    @NotNull @PositiveOrZero
    private Long rechargingAmount;

    public RechargingMoneyRequestCommand(Long membershipId, Long rechargingAmount) {
        this.membershipId = membershipId;
        this.rechargingAmount = rechargingAmount;
        this.validateSelf();
    }
}
