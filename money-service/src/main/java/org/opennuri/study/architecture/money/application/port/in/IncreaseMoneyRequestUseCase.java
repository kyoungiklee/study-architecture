package org.opennuri.study.architecture.money.application.port.in;

import org.opennuri.study.architecture.money.domain.ChangingMoneyRequest;

public interface IncreaseMoneyRequestUseCase {
    ChangingMoneyRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command);
}
