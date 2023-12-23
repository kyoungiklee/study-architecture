package org.opennuri.study.architecture.remittance.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.opennuri.study.architecture.remittance.application.port.in.RequestRemittanceCommand;
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

    @Test
    @Order(1)
    @DisplayName("송금 요청을 한다.")
    void requestRemittance() {
        RequestRemittanceCommand command = RequestRemittanceCommand.builder()
                .senderId("1")
                .receiverId("2")
                .toBankName("toBankName")
                .toAccountNumber("toAccountNumber")
                .requestType(RemittanceType.valueOf("INTERNAL"))
                .amount(1000L)
                .description("description")
                .build();

        RemittanceRequest remittanceRequest = requestRemittanceService.requestRemittance(command);
        log.info("remittanceRequest: {}", remittanceRequest);
        assertThat(remittanceRequest.getSenderId()).isEqualTo("1");
        assertThat(remittanceRequest.getReceiverId()).isEqualTo("2");
        assertThat(remittanceRequest.getToBankName()).isEqualTo("toBankName");
        assertThat(remittanceRequest.getToAccountNumber()).isEqualTo("toAccountNumber");
        assertThat(remittanceRequest.getRequestType().toString()).isEqualTo("INTERNAL");
        assertThat(remittanceRequest.getAmount()).isEqualTo(1000L);
        assertThat(remittanceRequest.getRequestStatus().toString()).isEqualTo("MEMBERSHIP_CHECK_COMPLETE");
        assertThat(remittanceRequest.getUuid()).isNotNull();
    }
}