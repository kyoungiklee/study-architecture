package org.opennuri.study.architecture.money.adapter.in.web;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangingMoneyRequest {
    @NotNull
    private String membershipId; // 멤버십 아이디
    @Positive
    @NotNull
    private Long moneyAmount; // 금액
}