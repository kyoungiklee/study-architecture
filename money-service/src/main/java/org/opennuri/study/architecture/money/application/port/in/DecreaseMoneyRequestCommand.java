package org.opennuri.study.architecture.money.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 고객 money 감소 요청
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DecreaseMoneyRequestCommand {
    private Long membershipId;
    private Long moneyAmount;
}
