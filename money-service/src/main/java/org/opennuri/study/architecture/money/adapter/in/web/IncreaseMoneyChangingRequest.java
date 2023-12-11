package org.opennuri.study.architecture.money.adapter.in.web;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncreaseMoneyChangingRequest {
    @NotNull
    private String membershipId; // 멤버십 아이디
    @Positive
    @NotNull
    private Long moneyAmount; // 금액
}