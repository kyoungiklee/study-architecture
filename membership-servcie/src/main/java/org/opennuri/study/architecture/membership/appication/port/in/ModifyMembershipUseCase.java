package org.opennuri.study.architecture.membership.appication.port.in;

import common.UseCase;
import org.opennuri.study.architecture.membership.domain.Membership;

public interface ModifyMembershipUseCase {
    public Membership modifyMembership(ModifyMembershipCommand command);
}
