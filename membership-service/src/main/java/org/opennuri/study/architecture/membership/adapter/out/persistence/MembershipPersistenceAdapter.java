package org.opennuri.study.architecture.membership.adapter.out.persistence;


import lombok.RequiredArgsConstructor;
import org.opennuri.study.architecture.common.PersistenceAdapter;
import org.opennuri.study.architecture.membership.appication.port.out.FindMembershipPort;
import org.opennuri.study.architecture.membership.appication.port.out.ModifyMembershipPort;
import org.opennuri.study.architecture.membership.appication.port.out.RegisterMembershipPort;
import org.opennuri.study.architecture.membership.domain.Membership;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort, ModifyMembershipPort {

    private final SpringDataMembershipRepository membershipRepository;
    @Override
    public MembershipJpaEntity createMembership(
            Membership.MembershipName membershipName,
            Membership.MembershipEmail membershipEmail,
            Membership.MembershipAddress membershipAddress,
            Membership.MembershipIsValid membershipIsValid,
            Membership.MembershipIsCorp membershipIsCorp) {

        return membershipRepository.save(
                new MembershipJpaEntity(
                     membershipName.getNameValue(),
                     membershipEmail.getEmailValue(),
                     membershipAddress.getAddressValue(),
                     membershipIsValid.isValidValue(),
                     membershipIsCorp.isCorpValue()
                )
        );
    }

    @Override
    public MembershipJpaEntity findMembership(Membership.MembershipId membershipId) {
        Optional<MembershipJpaEntity> optionalEntity = membershipRepository.findById(membershipId.getMembershipId());

        return optionalEntity.orElseThrow();

    }

    @Override
    public MembershipJpaEntity modifyMembership(
            Membership.MembershipId membershipId
            , Membership.MembershipName membershipName
            , Membership.MembershipEmail membershipEmail
            , Membership.MembershipAddress membershipAddress
            , Membership.MembershipIsValid membershipIsValid
            , Membership.MembershipIsCorp membershipIsCorp) {

        //find
        MembershipJpaEntity jpaEntity  = membershipRepository.findById(Long.valueOf(membershipId.getMembershipId()))
                .orElseThrow();
        //modify
        jpaEntity.setMembershipId(Long.valueOf(membershipId.getMembershipId()));
        jpaEntity.setName(membershipName.getNameValue());
        jpaEntity.setEmail(membershipEmail.getEmailValue());
        jpaEntity.setAddress(membershipAddress.getAddressValue());
        jpaEntity.setCorp(membershipIsCorp.isCorpValue());
        jpaEntity.setValid(membershipIsValid.isValidValue());
        //save
        return membershipRepository.save(jpaEntity);
    }
}
