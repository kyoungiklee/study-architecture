package org.opennuri.study.architecture.money.application.port.out.membership;

public interface MembershipServicePort {
    MembershipStatus  getMembershipInfo(String membershipId);
}
