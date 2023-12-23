package org.opennuri.study.architecture.money.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.common.UseCase;
import org.opennuri.study.architecture.common.exception.BusinessCheckFailException;
import org.opennuri.study.architecture.common.exception.BusinessCreateException;
import org.opennuri.study.architecture.money.application.port.in.CreateMemberMoneyUseCase;
import org.opennuri.study.architecture.money.application.port.in.CreateMoneyRequestCommand;
import org.opennuri.study.architecture.money.application.port.out.CreateMemberMoneyPort;
import org.opennuri.study.architecture.money.application.port.out.membership.MembershipServicePort;
import org.opennuri.study.architecture.money.application.port.out.membership.MembershipStatus;
import org.opennuri.study.architecture.money.domain.MemberMoney;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class CreateMemberMoneyService implements CreateMemberMoneyUseCase {
    private final CreateMemberMoneyPort createMemberMoneyPort;
    private final MembershipServicePort membershipServicePort;

    @Override
    public MemberMoney createMemberMoney(CreateMoneyRequestCommand command) {
        // Memebership 유효성 validation check
        MembershipStatus membershipInfo = membershipServicePort.getMembershipInfo(String.valueOf(command.getMembershipId()));

        if (membershipInfo == null) {
            throw new BusinessCheckFailException("회원의 Membership 정보를 찾을 수 없습니다.");
        }

        if (membershipInfo.getMembershipStatusType() != MembershipStatus.MembershipStatusType.NORMAL) {
            throw new BusinessCheckFailException("회원의 Membership 상태가 NORMAL 아닙니다.");
        }
        // Member Money 생성
        MemberMoney memberMoney = createMemberMoneyPort.createMemberMoney(
                new MemberMoney.MembershipId(command.getMembershipId())
                , new MemberMoney.MoneyAmount(command.getMoneyAmount())
        );

        if (memberMoney != null) {
            return memberMoney;
        } else {
            throw new BusinessCreateException("회원의 Money를 생성하지 못했습니다.");
        }
    }
}
