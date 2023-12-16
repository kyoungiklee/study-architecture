package org.opennuri.study.architecture.banking.appication.port.out;

public interface GetMembershipPort {

    public MembershipStatus getMembership(String membershipId);
}
