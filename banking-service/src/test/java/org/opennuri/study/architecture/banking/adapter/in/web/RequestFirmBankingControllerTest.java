package org.opennuri.study.architecture.banking.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.opennuri.study.architecture.banking.adapter.out.persistence.SpringDataRegisteredBankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
@DisplayName(value = "Controller: RequestFirmBankingControllerTest(타행계좌 이체 요청 테스트)")
class RequestFirmBankingControllerTest {

    @Autowired
    private SpringDataRegisteredBankAccountRepository springDataRegisteredBankAccountRepository;

    @BeforeEach()
    void setUp() {
        springDataRegisteredBankAccountRepository.deleteAll();
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    @DisplayName("타행계좌로 이체를 요청한다.")
    void requestFirmBanking() throws Exception {
        //given
        RequestFirmBankingRequest request = RequestFirmBankingRequest.builder()
                .membershipId("1")
                .fromBankName("국민은행")
                .fromBankAccountNumber("1234567890")
                .toBankName("신한은행")
                .toBankAccountNumber("0987654321")
                .moneyAmount(10000L)
                .description("테스트")
                .build();
        //when //then
        mockMvc.perform(
                post("/banking/firmbanking/request")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}