package org.opennuri.study.architecture.money.application.service;

import org.junit.jupiter.api.*;
import org.opennuri.study.architecture.money.adapter.out.persistence.SpringDataChangingMoneyPersistence;
import org.opennuri.study.architecture.money.adapter.out.persistence.SpringDataMemberMoneyPersistence;
import org.opennuri.study.architecture.money.application.port.in.CreateMoneyRequestCommand;
import org.opennuri.study.architecture.money.application.port.in.DecreaseMoneyRequestCommand;
import org.opennuri.study.architecture.money.domain.MemberMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@DisplayName(value = "고객 money를 사용하는 서비스 테스트")
@ActiveProfiles(value = "test")
class DecreaseMoneyRequestServiceTest {

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
    private DecreaseMoneyRequestService decreaseMoneyRequestService;

    @Autowired
    private CreateMemberMoneyService createMemberMoneyService;

    @Test
    @Order(1)
    @DisplayName(value = "고객 money를 감소시킨다.")
    void decreaseMoneyRequest() {
        // given

        createMemberMoneyService.createMemberMoney(CreateMoneyRequestCommand.builder()
                .membershipId(1L)
                .moneyAmount(10000L)
                .build());

        DecreaseMoneyRequestCommand command = DecreaseMoneyRequestCommand.builder()
                .membershipId(1L)
                .moneyAmount(11000L)
                .build();
        MemberMoney memberMoney = decreaseMoneyRequestService.decreaseMoneyRequest(command);
        // when // then
        assertThat(memberMoney.getMoneyAmount()).isEqualTo(9000L);
    }
}