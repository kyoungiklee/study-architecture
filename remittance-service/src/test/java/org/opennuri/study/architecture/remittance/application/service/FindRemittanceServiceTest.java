package org.opennuri.study.architecture.remittance.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.opennuri.study.architecture.remittance.adapter.out.persistence.RemittanceRequestRepository;
import org.opennuri.study.architecture.remittance.application.port.in.RemittanceSearchCommand;
import org.opennuri.study.architecture.remittance.application.port.in.RequestRemittanceCommand;
import org.opennuri.study.architecture.remittance.common.RemittanceType;
import org.opennuri.study.architecture.remittance.domain.RemittanceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@DisplayName(value = "Service: FindRemittanceService 테스트")
@Slf4j
@ActiveProfiles(value = "test")
class FindRemittanceServiceTest {

    @Autowired
    private FindRemittanceService findRemittanceService;

    @Autowired
    private RequestRemittanceService requestRemittanceService;

    @Autowired
    private RemittanceRequestRepository remittanceRequestRepository;


    @BeforeEach
    void setUp() {
        remittanceRequestRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        remittanceRequestRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName(value = "송금내역을 조회한다.")
    void findRemittanceHistory() {
        RequestRemittanceCommand command = RequestRemittanceCommand.builder()
                .senderId(1L)
                .receiverId(2L)
                .toBankName("toBankName")
                .toAccountNumber("toAccountNumber")
                .requestType(RemittanceType.valueOf("INTERNAL"))
                .amount(1000L)
                .description("내부고객 송금 테스트")
                .build();
        //given
        RemittanceRequest remittanceRequest = requestRemittanceService.requestRemittance(command);
        log.info("remittanceRequest: {}", remittanceRequest);

        RemittanceSearchCommand remittanceSearchCommand = RemittanceSearchCommand.builder()
                .senderId(1L)
                .build();
        //when
        List<RemittanceRequest> remittanceHistory = findRemittanceService.findRemittanceHistory(remittanceSearchCommand);
        log.info("remittanceHistory: {}", remittanceHistory);

        //then
        assertThat(remittanceHistory).isNotNull();
        assertThat(remittanceHistory.size()).isEqualTo(5);
        assertThat(remittanceHistory.get(0).getRequestType()).isEqualTo(RemittanceType.INTERNAL);
        assertThat(remittanceHistory.get(0).getSenderId()).isEqualTo(1L);
    }
}