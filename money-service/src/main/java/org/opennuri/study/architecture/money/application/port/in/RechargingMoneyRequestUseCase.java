package org.opennuri.study.architecture.money.application.port.in;

import org.opennuri.study.architecture.money.domain.MemberMoney;

public interface RechargingMoneyRequestUseCase {
    MemberMoney rechargingMoney(RechargingMoneyRequestCommand command);
}
