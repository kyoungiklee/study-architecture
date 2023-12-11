package org.opennuri.study.architecture.money.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncreaseMoneyChangingRequest {
    private String membershipId; // 멤버십 아이디
    private Long moneyAmount; // 금액
}