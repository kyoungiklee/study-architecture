package org.opennuri.study.architecture.money.application.port.in;

import org.opennuri.study.architecture.common.exception.BusinessCheckFailException;
import org.opennuri.study.architecture.money.domain.MemberMoney;
import org.springframework.web.server.ServerErrorException;

public interface FindMemberMoneyUseCase {
    MemberMoney findMemberMoney(Long membershipId) throws BusinessCheckFailException, ServerErrorException;
}
