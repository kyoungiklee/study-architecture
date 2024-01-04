package org.opennuri.study.architecture.money.application.port.in;

import org.opennuri.study.architecture.money.domain.MemberMoney;

public interface IncreaseMoneyRequestUseCase {
    MemberMoney increaseMoneyRequest(IncreaseMoneyRequestCommand command);

    MemberMoney increaseMoneyRequestByEvent(IncreaseMoneyRequestCommand command);
}
