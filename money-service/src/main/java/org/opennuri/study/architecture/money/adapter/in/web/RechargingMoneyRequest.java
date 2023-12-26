package org.opennuri.study.architecture.money.adapter.in.web;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RechargingMoneyRequest {
    private Long membershipId;
    private Long rechargingAmount;
}
