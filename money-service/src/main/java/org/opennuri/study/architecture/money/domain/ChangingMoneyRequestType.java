package org.opennuri.study.architecture.money.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChangingMoneyRequestType {
    DEPOSIT("적립"),
    WITHDRAW("사용"),
    RECHARGING("충전");

    private final String description;
}

