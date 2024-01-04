package org.opennuri.study.architecture.money.adapter.out.persistence;

import org.opennuri.study.architecture.money.domain.MemberMoney;

public class MemberMoneyMapper {
    public static MemberMoney mapToMemberMoney(MemberMoneyJpaEntity savedEntity) {
        return  MemberMoney.from(
                new MemberMoney.MemberMoneyId(savedEntity.getMemberMoneyId())
                , new MemberMoney.MembershipId(savedEntity.getMembershipId())
                , new MemberMoney.Balance(savedEntity.getBalance())
                , new MemberMoney.AggregateId(savedEntity.getAggregateId())
        );
    }
}
