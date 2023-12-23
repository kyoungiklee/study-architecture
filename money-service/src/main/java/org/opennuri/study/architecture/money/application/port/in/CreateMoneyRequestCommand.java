package org.opennuri.study.architecture.money.application.port.in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.opennuri.study.architecture.common.SelfValidating;

/**
 * member money 생성 요청 command
 */

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class CreateMoneyRequestCommand extends SelfValidating<CreateMoneyRequestCommand> {

    @NotNull
    private Long membershipId;
    @NotNull @Positive
    private Long moneyAmount;

    private CreateMoneyRequestCommand(Long membershipId, Long moneyAmount) {
        this.membershipId = membershipId;
        this.moneyAmount = moneyAmount;
        this.validateSelf();
    }

    public static CreateMoneyRequestCommand from(Long membershipId, Long moneyAmount) {
        return new CreateMoneyRequestCommand(membershipId, moneyAmount);
    }
}
