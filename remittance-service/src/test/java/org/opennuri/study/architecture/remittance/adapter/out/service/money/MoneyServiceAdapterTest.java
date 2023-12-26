package org.opennuri.study.architecture.remittance.adapter.out.service.money;

import org.junit.jupiter.api.*;
import org.opennuri.study.architecture.remittance.application.port.out.money.MoneyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(value = "test")
@DisplayName(value = "MoneyServiceAdapter 테스트")
class MoneyServiceAdapterTest {

    @Autowired
    private MoneyServiceAdapter moneyServiceAdapter;

    @Test
    @Order(1)
    @DisplayName(value = "고객의 money를 조회한다.")
    void getMoneyInfo() {
        MoneyInfo moneyInfo = moneyServiceAdapter.getMoneyInfo(1L);
        assertThat(moneyInfo.getMembershipId()).isEqualTo("1");
        assertThat(moneyInfo.getBalance()).isInstanceOf(Long.class);
        assertThat(moneyInfo.isValid()).isEqualTo(true);
        assertThat(moneyInfo.getMessage()).isEqualTo("SUCCESS");
    }

    @Test
    void requestMoneyRecharge() {
    }

    @Test
    @Order(3)
    @DisplayName(value = "고객의 money를 충전한다.")
    void requestMoneyIncrease() {
        MoneyResponse increaseMoneyResponse = moneyServiceAdapter.requestMoneyIncrease(1L, 1000L);
        assertThat(increaseMoneyResponse.getMembershipId()).isEqualTo(1L);
        assertThat(increaseMoneyResponse.getStatus()).isEqualTo("SUCCESS");
    }

    @Test
    @Order(4)
    @DisplayName(value = "고객의 money를 출금한다.")
    void requestMoneyDecrease() {
    }
}