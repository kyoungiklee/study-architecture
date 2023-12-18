package org.opennuri.study.architecture.money.adapter.out.service;

import org.junit.jupiter.api.*;
import org.opennuri.study.architecture.money.application.port.out.membership.MembershipStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class MembershipServiceAdapterTest {

    @Autowired
    MembershipServiceAdapter membershipServiceAdapter;
    @Test
    @Order(1)
    @DisplayName("회원정보 조회")
    void getMembershipInfo() {
        MembershipStatus membershipStatus = membershipServiceAdapter.getMembershipInfo("1");
        assertEquals(membershipStatus.getMembershipId(), "1");
        assertEquals(membershipStatus.getMembershipStatusType(), MembershipStatus.MembershipStatusType.NORMAL);
    }
}