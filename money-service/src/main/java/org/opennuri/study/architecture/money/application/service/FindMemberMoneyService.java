package org.opennuri.study.architecture.money.application.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.common.UseCase;
import org.opennuri.study.architecture.common.exception.BusinessCheckFailException;
import org.opennuri.study.architecture.money.application.port.in.FindMemberMoneyUseCase;
import org.opennuri.study.architecture.money.application.port.out.FindMemberMoneyPort;
import org.opennuri.study.architecture.money.domain.MemberMoney;
import org.springframework.web.server.ServerErrorException;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class FindMemberMoneyService implements FindMemberMoneyUseCase {
    private final FindMemberMoneyPort findMemberMoneyPort;
    @Override
    public MemberMoney findMemberMoney(Long membershipId) {
        MemberMoney memberMoney;

        try {
            memberMoney = findMemberMoneyPort.findMemberMoney(membershipId);
        } catch (Exception e) {
            log.error("멤버쉽 money 조회 실패. 멤버쉽 아이디: {}", membershipId, e);
            throw new ServerErrorException("멤버쉽 money 조회 실패. 멤버쉽 아이디: " + membershipId, e);
        }

        if (memberMoney != null) {
            return memberMoney;
        } else {
            log.error("멤버쉽 momey가 존재하지 않습니다. 멤버쉽 아이디: {}", membershipId);
            throw new BusinessCheckFailException("멤버쉽 money가 존재하지 않습니다.");
        }
    }
}
