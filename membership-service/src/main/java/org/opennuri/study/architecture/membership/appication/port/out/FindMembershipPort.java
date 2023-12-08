package org.opennuri.study.architecture.membership.appication.port.out;

import org.opennuri.study.architecture.membership.adapter.out.persistence.MembershipJpaEntity;
import org.opennuri.study.architecture.membership.domain.Membership;

public interface FindMembershipPort {

    MembershipJpaEntity findMembership(
            Membership.MembershipId membershipId
    );
}
