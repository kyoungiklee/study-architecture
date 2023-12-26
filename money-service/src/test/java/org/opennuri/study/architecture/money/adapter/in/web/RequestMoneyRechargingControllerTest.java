package org.opennuri.study.architecture.money.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.opennuri.study.architecture.money.adapter.out.persistence.SpringDataChangingMoneyPersistence;
import org.opennuri.study.architecture.money.application.port.in.CreateMoneyRequestCommand;
import org.opennuri.study.architecture.money.application.service.CreateMemberMoneyService;
import org.opennuri.study.architecture.money.domain.MemberMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(value = "test")
@DisplayName("Contoller: RequestMoneyRechargingController")
@Slf4j
class RequestMoneyRechargingControllerTest {

    @Autowired
    SpringDataChangingMoneyPersistence springDataChangingMoneyPersistence;
    @Autowired
    SpringDataChangingMoneyPersistence springDataMemberMoneyPersistence;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext context;
    private MockMvc mockMvc;

    @Autowired
    CreateMemberMoneyService createMemberMoneyService;

    @BeforeEach
    void setUp() {
        springDataMemberMoneyPersistence.deleteAll();
        springDataChangingMoneyPersistence.deleteAll();

        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                })).build();

    }

    @Test
    @DisplayName("고객 money를 충전 요청한다.")
    void requestMoneyRecharging() throws Exception {

        // given

        MemberMoney memberMoney = createMemberMoneyService.createMemberMoney(
                CreateMoneyRequestCommand.builder()
                        .membershipId(1L)
                        .moneyAmount(1000L)
                        .build());
        log.info("memberMoney: {}", memberMoney);

        Long membershipId = 1L;
        Long rechargingAmount = 1000L;

        RechargingMoneyRequest rechargingMoneyRequest = RechargingMoneyRequest.builder()
                .membershipId(membershipId)
                .rechargingAmount(rechargingAmount)
                .build();


        // when
        // then
            mockMvc.perform(MockMvcRequestBuilders.post("/money/recharging")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(rechargingMoneyRequest)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(jsonPath("$.membershipId").value(membershipId))
                    .andExpect(jsonPath("$.balance").value(2000L))
                    .andExpect(jsonPath("$.valid").value(true))
                    .andExpect(jsonPath("$.message").value("SUCCESS"))
                    .andDo(print());
    }
}