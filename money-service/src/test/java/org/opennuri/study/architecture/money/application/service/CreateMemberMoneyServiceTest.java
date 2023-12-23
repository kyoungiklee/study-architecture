package org.opennuri.study.architecture.money.application.service;

import org.junit.jupiter.api.*;
import org.opennuri.study.architecture.money.adapter.out.persistence.SpringDataChangingMoneyPersistence;
import org.opennuri.study.architecture.money.adapter.out.persistence.SpringDataMemberMoneyPersistence;
import org.opennuri.study.architecture.money.application.port.in.CreateMoneyRequestCommand;
import org.opennuri.study.architecture.money.domain.MemberMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@DisplayName(value = "고객 Money 생성 테스트")
@ActiveProfiles(value = "test")
class CreateMemberMoneyServiceTest {

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
    private CreateMemberMoneyService createMemberMoneyService;

    @Test
    @Order(value = 1)
    @DisplayName(value = "고객 Money를 생성한다.")
    void createMemberMoney() {
        //given
        CreateMoneyRequestCommand command = CreateMoneyRequestCommand.builder()
                .membershipId(1L)
                .moneyAmount(1000L)
                .build();
        //when
        MemberMoney memberMoney = createMemberMoneyService.createMemberMoney(command);
        //then
        assertThat(memberMoney).isNotNull();
        assertThat(memberMoney.getMembershipId()).isEqualTo(1L);
        assertThat(memberMoney.getMoneyAmount()).isEqualTo(1000L);
    }
}