package org.opennuri.study.architecture.membership.appication.service;


import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.common.UseCase;
import org.opennuri.study.architecture.membership.adapter.out.persistence.MembershipJpaEntity;
import org.opennuri.study.architecture.membership.adapter.out.persistence.MembershipMapper;
import org.opennuri.study.architecture.membership.appication.port.in.RegisterMembershipCommand;
import org.opennuri.study.architecture.membership.appication.port.in.RegisterMembershipUseCase;
import org.opennuri.study.architecture.membership.appication.port.out.RegisterMembershipPort;
import org.opennuri.study.architecture.membership.domain.Membership;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterMembershipService implements RegisterMembershipUseCase {

    @NonNull
    private final RegisterMembershipPort registerMembershipPort;

    @NonNull
    private final MembershipMapper membershipMapper;
    @Override
    public Membership resisterMembership(RegisterMembershipCommand command) {

        log.info(command.getAddress());

        MembershipJpaEntity jpaEntity = registerMembershipPort.createMembership(
                new Membership.MembershipName(command.getName()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipAddress(command.getAddress()),
                new Membership.MembershipIsValid(command.isValid()),
                new Membership.MembershipIsCorp(command.isCorp())
        );

        //entity --> Membership
        return membershipMapper.mapToDomainEntity(jpaEntity);
    }
}
