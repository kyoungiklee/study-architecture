package org.opennuri.study.architecture.money.adapter.in.web;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateMoneyRequest {
    private Long membershipId;
    private Long moneyAmount;
}
