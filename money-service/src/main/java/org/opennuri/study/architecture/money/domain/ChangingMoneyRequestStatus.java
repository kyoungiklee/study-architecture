package org.opennuri.study.architecture.money.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChangingMoneyRequestStatus {
    REQUESTED("요청"), // 요청됨
    SUCCESS("성공"), // 성공
    FAILED("실패"); // 실패

    private final String description;
}
