package org.opennuri.study.architecture.money.application.port.in;

import org.opennuri.study.architecture.money.domain.MoneyChangingRequest;

public interface IncreaseMoneyRequestUseCase {
    MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command);

    MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command);
}
