package org.opennuri.study.architecture.remittance.adapter.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opennuri.study.architecture.remittance.adapter.out.service.money.MoneyServiceAdapter;
import org.opennuri.study.architecture.remittance.application.port.out.money.MoneyInfo;
import org.opennuri.study.architecture.remittance.common.RemittanceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@Slf4j
@DisplayName("Controller: RequestRemittanceController(송금요청 컨트롤러 테스트)")
@ActiveProfiles("test")
@AutoConfigureMockMvc
class RequestRemittanceControllerTest {

    @Autowired
    MoneyServiceAdapter moneyServiceAdapter;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void requestRemittance() throws Exception {
        //given
        // 테스트를 위해 먼저 돈을 충전한다.
        moneyServiceAdapter.requestMoneyIncrease(1L, 10000L);
        // 송금요청을 위한 요청 VO를 생성한다.
        RemittanceRequestVO request = RemittanceRequestVO.builder()
                .senderId(1L)
                .receiverId(2L)
                .toBankName("") //internal인 경우 은행이름은 필요없다.
                .toAccountNumber("") //internal인 경우 계좌번호는 필요없다.
                .requestType(RemittanceType.INTERNAL)
                .amount(10000L)
                .description("테스트")
                .build();
        log.info("request: {}", request);
        //when //then
        MoneyInfo beforSenderMoneyInfo = moneyServiceAdapter.getMoneyInfo(1L);
        MoneyInfo beforRecieverMoneyInfo = moneyServiceAdapter.getMoneyInfo(2L);
        log.info("beforSenderMoneyInfo: {}", beforSenderMoneyInfo);
        log.info("beforRecieverMoneyInfo: {}", beforRecieverMoneyInfo);
        // 송금요청 컨트롤러를 호출한다.
        mockMvc.perform(MockMvcRequestBuilders.post("/remittance")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(result -> log.info("result: {}", result.getResponse().getContentAsString())
                )
                .andExpect(jsonPath("$.remittanceId").exists())
                .andExpect(jsonPath("$.message").value("SUCCESS"));

        // 송금요청 후 송금자의 잔액과 수신자의 잔액을 확인한다.
        MoneyInfo afterSenderMoneyInfo = moneyServiceAdapter.getMoneyInfo(1L);
        MoneyInfo afterRecieverMoneyInfo = moneyServiceAdapter.getMoneyInfo(2L);
        log.info("afterSenderMoneyInfo: {}", afterSenderMoneyInfo);
        log.info("afterRecieverMoneyInfo: {}", afterRecieverMoneyInfo);

        assertEquals(beforSenderMoneyInfo.getBalance() - afterSenderMoneyInfo.getBalance(), (long) request.getAmount());
        assertEquals(afterRecieverMoneyInfo.getBalance() - beforRecieverMoneyInfo.getBalance(), (long) request.getAmount());

    }
}