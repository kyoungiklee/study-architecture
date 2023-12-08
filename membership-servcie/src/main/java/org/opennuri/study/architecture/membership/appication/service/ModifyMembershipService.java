package org.opennuri.study.architecture.membership.appication.service;

import common.UseCase;
import lombok.RequiredArgsConstructor;
import org.opennuri.study.architecture.membership.adapter.out.persistence.MembershipJpaEntity;
import org.opennuri.study.architecture.membership.adapter.out.persistence.MembershipMapper;
import org.opennuri.study.architecture.membership.appication.port.in.ModifyMembershipCommand;
import org.opennuri.study.architecture.membership.appication.port.in.ModifyMembershipUseCase;
import org.opennuri.study.architecture.membership.appication.port.out.ModifyMembershipPort;
import org.opennuri.study.architecture.membership.domain.Membership;

@UseCase
@RequiredArgsConstructor
public class ModifyMembershipService implements ModifyMembershipUseCase {

    private final ModifyMembershipPort modifyMembershipPort;
    private final MembershipMapper membershipMapper;
    @Override
    public Membership modifyMembership(ModifyMembershipCommand command) {

        MembershipJpaEntity jpaEntity = modifyMembershipPort.modifyMembership(
                new Membership.MembershipId(command.getMembershipId())
                , new Membership.MembershipName(command.getName())
                , new Membership.MembershipEmail(command.getEmail())
                , new Membership.MembershipAddress(command.getAddress())
                , new Membership.MembershipIsValid(command.isValid())
                , new Membership.MembershipIsCorp(command.isCorp())
        );
        return membershipMapper.mapToDomainEntity(jpaEntity);
    }
}
