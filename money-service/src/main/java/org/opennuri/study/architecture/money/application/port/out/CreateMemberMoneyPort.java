package org.opennuri.study.architecture.money.application.port.out;

import org.opennuri.study.architecture.money.domain.MemberMoney;

public interface CreateMemberMoneyPort {
    MemberMoney createMemberMoney(MemberMoney.MembershipId membershipId,
                                  MemberMoney.Balance balance,
                                  MemberMoney.AggregateId aggregateId);

}