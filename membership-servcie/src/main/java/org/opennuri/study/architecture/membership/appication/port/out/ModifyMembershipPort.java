package org.opennuri.study.architecture.membership.appication.port.out;

import org.opennuri.study.architecture.membership.adapter.out.persistence.MembershipJpaEntity;
import org.opennuri.study.architecture.membership.domain.Membership;

public interface ModifyMembershipPort {

    MembershipJpaEntity modifyMembership(
            Membership.MembershipId membershipId,
            Membership.MembershipName membershipName,
            Membership.MembershipEmail membershipEmail,
            Membership.MembershipAddress membershipAddress,
            Membership.MembershipIsValid membershipIsValid,
            Membership.MembershipIsCorp membershipIsCorp
    );


}
