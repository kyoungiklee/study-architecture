package org.opennuri.study.architecture.money.application.port.in;

import org.opennuri.study.architecture.money.domain.MemberMoney;

public interface DecreaseMoneyRequestUseCase {

    MemberMoney decreaseMoneyRequest(DecreaseMoneyRequestCommand command);
}
