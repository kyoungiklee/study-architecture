package org.opennuri.study.architecture.membership.appication.port.in;

import org.opennuri.study.architecture.membership.domain.Membership;

public interface RegisterMembershipUseCase {

    Membership resisterMembership(RegisterMembershipCommand command);
}
