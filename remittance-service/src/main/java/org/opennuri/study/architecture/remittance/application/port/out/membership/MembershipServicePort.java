package org.opennuri.study.architecture.remittance.application.port.out.membership;

public interface MembershipServicePort {
    MembershipInfo getMembershipStatus(Long membershipId);
}
