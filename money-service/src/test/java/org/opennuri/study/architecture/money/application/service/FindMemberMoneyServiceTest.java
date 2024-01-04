package org.opennuri.study.architecture.money.application.service;

import org.junit.jupiter.api.*;
import org.opennuri.study.architecture.common.exception.BusinessCheckFailException;
import org.opennuri.study.architecture.money.adapter.out.persistence.SpringDataChangingMoneyPersistence;
import org.opennuri.study.architecture.money.adapter.out.persistence.SpringDataMemberMoneyPersistence;
import org.opennuri.study.architecture.money.application.port.in.CreateMoneyRequestCommand;
import org.opennuri.study.architecture.money.domain.MemberMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@DisplayName(value = "Service: FindMemberMoneyServiceTest")
@ActiveProfiles(value = "test")
class FindMemberMoneyServiceTest {
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

    @Autowired
    private SpringDataChangingMoneyPersistence springDataChangingMoneyPersistence;

    @Autowired
    private SpringDataMemberMoneyPersistence springDataMemberMoneyPersistence;

    @Autowired
    FindMemberMoneyService findMemberMoneyService;

    @Autowired
    CreateMemberMoneyService createMemberMoneyService;

    @Test
    @Order(1)
    @DisplayName(value = "고객 money를 조회한다.")
    void findMemberMoney() {
        //given
        CreateMoneyRequestCommand command = CreateMoneyRequestCommand.builder()
                .membershipId(1L)
                .moneyAmount(1000L)
                .build();
        createMemberMoneyService.createMemberMoney(command);

        //when
        MemberMoney memberMoney = findMemberMoneyService.findMemberMoney(1L);

        //then
        assertThat(memberMoney.getMembershipId()).isEqualTo(1L);
        assertThat(memberMoney.getBalance()).isEqualTo(1000L);

        assertThatThrownBy(() -> findMemberMoneyService.findMemberMoney(2L))
                .isInstanceOf(BusinessCheckFailException.class)
                .hasMessageContaining("멤버쉽 money가 존재하지 않습니다.");
    }
}