package org.opennuri.study.architecture.money.application.port.out;

import org.opennuri.study.architecture.money.domain.MemberMoney;

public interface FindMemberMoneyPort {
    MemberMoney findMemberMoney(Long membershipId);
}
