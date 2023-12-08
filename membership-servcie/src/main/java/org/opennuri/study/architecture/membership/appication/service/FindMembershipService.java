package org.opennuri.study.architecture.membership.appication.service;

import common.UseCase;
import lombok.RequiredArgsConstructor;
import org.opennuri.study.architecture.membership.adapter.out.persistence.MembershipJpaEntity;
import org.opennuri.study.architecture.membership.adapter.out.persistence.MembershipMapper;
import org.opennuri.study.architecture.membership.appication.port.in.FindMembershipCommand;
import org.opennuri.study.architecture.membership.appication.port.in.FindMembershipUseCase;
import org.opennuri.study.architecture.membership.appication.port.out.FindMembershipPort;
import org.opennuri.study.architecture.membership.domain.Membership;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class FindMembershipService implements FindMembershipUseCase {

    private final FindMembershipPort findMembershipPort;
    private final MembershipMapper membershipMapper;
    @Override
    public Membership findMembership(FindMembershipCommand command) {

        MembershipJpaEntity jpaEntity = findMembershipPort.findMembership(
                new Membership.MembershipId(command.getMembershipId())
        );

        return membershipMapper.mapToDomainEntity(jpaEntity);
    }
}
