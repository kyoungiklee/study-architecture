package org.opennuri.study.architecture.remittance.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.opennuri.study.architecture.remittance.adapter.out.service.money.MoneyResponse;
import org.opennuri.study.architecture.remittance.adapter.out.service.money.MoneyServiceAdapter;
import org.opennuri.study.architecture.remittance.application.port.in.RequestRemittanceCommand;
import org.opennuri.study.architecture.remittance.application.port.out.money.MoneyInfo;
import org.opennuri.study.architecture.remittance.common.RemittanceType;
import org.opennuri.study.architecture.remittance.domain.RemittanceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@DisplayName(value = "RequestRemittanceService 테스트")
@ActiveProfiles(value = "test")
class RequestRemittanceServiceTest {

    @Autowired
    RequestRemittanceService requestRemittanceService;

    @Autowired
    private MoneyServiceAdapter moneyServiceAdapter;

    @Test
    @Order(1)
    @DisplayName("내부 고객에게 송금을 요청을 한다.")
    void requestRemittanceInternal() {
        //given
        MoneyResponse moneyResponse = moneyServiceAdapter.requestMoneyIncrease(1L, 1000L);
        log.info("moneyResponse: {}", moneyResponse);
        RequestRemittanceCommand command = RequestRemittanceCommand.builder()
                .senderId(1L)
                .receiverId(2L)
                .toBankName("toBankName")
                .toAccountNumber("toAccountNumber")
                .requestType(RemittanceType.valueOf("INTERNAL"))
                .amount(1000L)
                .description("내부고객 송금 테스트")
                .build();

        //when
        MoneyInfo before = moneyServiceAdapter.getMoneyInfo(1L);
        RemittanceRequest remittanceRequest = null;
        for (int i = 0; i < 1000; i++) {
            remittanceRequest = requestRemittanceService.requestRemittance(command);
            log.info("remittanceRequest: {}", remittanceRequest);
        }

        MoneyInfo after = moneyServiceAdapter.getMoneyInfo(1L);

        //then
        log.info("remittanceRequest: {}", remittanceRequest);
        assertThat(remittanceRequest.getSenderId()).isEqualTo(1L);
        assertThat(remittanceRequest.getReceiverId()).isEqualTo(2L);
        assertThat(remittanceRequest.getToBankName()).isEqualTo("toBankName");
        assertThat(remittanceRequest.getToAccountNumber()).isEqualTo("toAccountNumber");
        assertThat(remittanceRequest.getRequestType().toString()).isEqualTo("INTERNAL");
        assertThat(after.getBalance()).isEqualTo(before.getBalance() - (command.getAmount()));
        assertThat(remittanceRequest.getRequestStatus().toString()).isEqualTo("COMPLETE");
        assertThat(remittanceRequest.getUuid()).isNotNull();
    }

    @Test
    @Order(2)
    @DisplayName("외부 은행으로 송금 요청을 한다.")
    void requestRemittanceExternal() {
        //given
        RequestRemittanceCommand command = RequestRemittanceCommand.builder()
                .senderId(1L)
                .toBankName("toBankName")
                .toAccountNumber("toAccountNumber")
                .requestType(RemittanceType.valueOf("EXTERNAL"))
                .amount(1000L)
                .description("외부은행 송금 테스트")
                .build();
        MoneyInfo before = moneyServiceAdapter.getMoneyInfo(1L);
        log.info("before: {}", before);
        //when
        RemittanceRequest remittanceRequest = requestRemittanceService.requestRemittance(command);
        log.info("remittanceRequest: {}", remittanceRequest);


        //then
        MoneyInfo after = moneyServiceAdapter.getMoneyInfo(1L);
        log.info("after: {}", after);

        assertThat(remittanceRequest.getSenderId()).isEqualTo(1L);
        assertThat(remittanceRequest.getToBankName()).isEqualTo("toBankName");
        assertThat(remittanceRequest.getToAccountNumber()).isEqualTo("toAccountNumber");
        assertThat(remittanceRequest.getRequestType().toString()).isEqualTo("EXTERNAL");
        assertThat(after.getBalance()).isEqualTo(before.getBalance() - command.getAmount());
        assertThat(remittanceRequest.getRequestStatus().toString()).isEqualTo("COMPLETE");
        assertThat(remittanceRequest.getUuid()).isNotNull();
    }

}