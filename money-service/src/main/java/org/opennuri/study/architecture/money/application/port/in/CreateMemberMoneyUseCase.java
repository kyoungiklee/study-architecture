package org.opennuri.study.architecture.money.application.port.in;

import org.opennuri.study.architecture.money.domain.MemberMoney;

public interface CreateMemberMoneyUseCase {
    MemberMoney createMemberMoney(CreateMoneyRequestCommand command);
}
