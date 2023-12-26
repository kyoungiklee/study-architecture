package org.opennuri.study.architecture.money.application.port.in;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.opennuri.study.architecture.common.SelfValidating;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class RechargingMoneyRequestCommand  extends SelfValidating<RechargingMoneyRequestCommand> {

    @NotNull
    private Long membershipId;
    @NotNull @Positive @Max(value = 1000000L) // 100만원 이하만 가능(테스트용)
    private Long amount;

    public RechargingMoneyRequestCommand(Long membershipId, Long amount) {
        this.membershipId = membershipId;
        this.amount = amount;

        this.validateSelf();
    }
}
