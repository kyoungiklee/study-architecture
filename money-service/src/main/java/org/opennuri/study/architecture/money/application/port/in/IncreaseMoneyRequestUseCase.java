package org.opennuri.study.architecture.money.application.port.in;

import org.opennuri.study.architecture.money.domain.ChangingMoneyRequest;

public interface IncreaseMoneyRequestUseCase {
    public ChangingMoneyRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command);
}
