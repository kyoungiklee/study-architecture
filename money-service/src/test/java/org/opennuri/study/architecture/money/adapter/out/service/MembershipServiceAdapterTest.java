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
@DisplayName(value = "멤버십 서비스 어댑터 테스트")
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
    @DisplayName("회원정보 조회")
    void getMembershipInfo() {
        MembershipStatus membershipStatus = membershipServiceAdapter.getMembershipInfo("1");
        assertEquals(membershipStatus.getMembershipId(), "1");
        assertEquals(membershipStatus.getMembershipStatusType(), MembershipStatus.MembershipStatusType.NORMAL);
    }
}