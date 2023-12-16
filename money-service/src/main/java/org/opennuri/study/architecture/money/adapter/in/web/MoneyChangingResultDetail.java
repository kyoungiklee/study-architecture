package org.opennuri.study.architecture.money.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.opennuri.study.architecture.money.domain.ChangingMoneyRequestStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyChangingResultDetail {
    private String membershipId;
    private Long amount;
    private ChangingMoneyRequestStatus status;
}
