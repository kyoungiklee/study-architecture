package org.opennuri.study.architecture.money.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChangingMoneyRequestType {
    DEPOSIT("충전"),
    WITHDRAW("사용");

    private final String description;
}

