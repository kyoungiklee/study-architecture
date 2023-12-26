package org.opennuri.study.architecture.money.adapter.in.web;

import org.junit.jupiter.api.*;
import org.opennuri.study.architecture.money.adapter.out.persistence.SpringDataChangingMoneyPersistence;
import org.opennuri.study.architecture.money.adapter.out.persistence.SpringDataMemberMoneyPersistence;
import org.opennuri.study.architecture.money.application.port.in.CreateMoneyRequestCommand;
import org.opennuri.study.architecture.money.application.service.CreateMemberMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@DisplayName(value = "Controoler: RequestMoneyFindControllerTest")
@ActiveProfiles(value = "test")
class RequestMoneyFindControllerTest {

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
    private MockMvc mockMvc;

    @Autowired
    CreateMemberMoneyService createMemberMoneyService;

    @Test
    @Order(1)
    @DisplayName(value = "고객의 money를 조회한다.")
    void findMoney() throws Exception {

        //given
        CreateMoneyRequestCommand command = CreateMoneyRequestCommand.builder()
                .membershipId(1L)
                .moneyAmount(1000L)
                .build();
        createMemberMoneyService.createMemberMoney(command);

        //when & then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/money/find/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.membershipId").value("1"))
                .andExpect(jsonPath("$.balance").value("1000"))
                .andExpect(jsonPath("$.valid").value(true))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
        ;

        mockMvc.perform(
                MockMvcRequestBuilders.get("/money/find/2"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath("$.message").value("멤버쉽 money가 존재하지 않습니다."));
    }
}