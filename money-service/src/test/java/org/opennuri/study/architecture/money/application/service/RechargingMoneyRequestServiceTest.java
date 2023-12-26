package org.opennuri.study.architecture.money.application.service;

import org.junit.jupiter.api.*;
import org.opennuri.study.architecture.money.adapter.out.persistence.MemberMoneyJpaEntity;
import org.opennuri.study.architecture.money.adapter.out.persistence.SpringDataChangingMoneyPersistence;
import org.opennuri.study.architecture.money.adapter.out.persistence.SpringDataMemberMoneyPersistence;
import org.opennuri.study.architecture.money.application.port.in.RechargingMoneyRequestCommand;
import org.opennuri.study.architecture.money.domain.MemberMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Service: RechargingMoneyRequestServiceTest")
@ActiveProfiles(value = "test")
class RechargingMoneyRequestServiceTest {

    @Autowired
    SpringDataChangingMoneyPersistence springDataChangingMoneyPersistence;

    @Autowired
    SpringDataMemberMoneyPersistence springDataMemberMoneyPersistence;

    @Autowired
    RechargingMoneyRequestService rechargingMoneyRequestService;



    @BeforeEach
    void setUp() {
        springDataChangingMoneyPersistence.deleteAll();
        springDataMemberMoneyPersistence.deleteAll();
    }

    @AfterEach
    void tearDown() {
        springDataChangingMoneyPersistence.deleteAll();
        springDataMemberMoneyPersistence.deleteAll();
    }

    @Test
    @DisplayName("고객 monye를 충전 시킨다.")
    void rechargingMoney() {

        // given

        springDataMemberMoneyPersistence.save(new MemberMoneyJpaEntity(
                1L
                , 1000L
        ));

        Long membershipId = 1L;
        Long rechargingAmount = 1000L;

        RechargingMoneyRequestCommand command = RechargingMoneyRequestCommand.builder()
                .membershipId(membershipId)
                .rechargingAmount(rechargingAmount)
                .build();

        // when
        MemberMoney memberMoney = rechargingMoneyRequestService.rechargingMoney(command);

        // then
        assertThat(memberMoney.getMembershipId()).isEqualTo(membershipId);
        assertThat(memberMoney.getMoneyAmount()).isEqualTo(2000L);
    }
}