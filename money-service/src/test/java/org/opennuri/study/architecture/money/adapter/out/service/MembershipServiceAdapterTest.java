package org.opennuri.study.architecture.money.adapter.out.service;

import org.junit.jupiter.api.*;
import org.opennuri.study.architecture.money.adapter.out.persistence.SpringDataChangingMoneyPersistence;
import org.opennuri.study.architecture.money.adapter.out.persistence.SpringDataMemberMoneyPersistence;
import org.opennuri.study.architecture.money.application.port.out.membership.MembershipStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(value = "test")
@DisplayName(value = "Adapter: MembershipServiceAdapterTest")
class MembershipServiceAdapterTest {

    @BeforeEach
    void setUp() {
        springDataChangingMoneyPersistence.deleteAll();
        springDataMemberMoneyPersistence.deleteAll();
    }

    @Autowired
    private SpringDataChangingMoneyPersistence springDataChangingMoneyPersistence;

    @Autowired
    private SpringDataMemberMoneyPersistence springDataMemberMoneyPersistence;

    @Autowired
    MembershipServiceAdapter membershipServiceAdapter;
    @Test
    @Order(1)
    @DisplayName("회원 정보를 조회한다(moeneyservice -> membershipservice)")
    void getMembershipInfo() {
        MembershipStatus membershipStatus = membershipServiceAdapter.getMembershipInfo("1");
        assertEquals(membershipStatus.getMembershipId(), "1");
        assertEquals(membershipStatus.getMembershipStatusType(), MembershipStatus.MembershipStatusType.NORMAL);
    }
}