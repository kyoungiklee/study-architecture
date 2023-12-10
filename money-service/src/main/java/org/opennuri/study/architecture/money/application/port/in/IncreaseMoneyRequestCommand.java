package org.opennuri.study.architecture.money.application.port.in;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.opennuri.study.architecture.common.SelfValidating;


@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class IncreaseMoneyRequestCommand extends SelfValidating<IncreaseMoneyRequestCommand> {
    @NotNull
    @NotBlank
    private final String membershipId; // 회원 식별자

    @NotNull
    @Positive
    @Max(value = 1000000L)
    private final Long moneyAmount; // 충전 또는 사용 금액

    public IncreaseMoneyRequestCommand(String membershipId, Long moneyAmount) {
        this.membershipId = membershipId;
        this.moneyAmount = moneyAmount;

        this.validateSelf();
    }
}
