package org.opennuri.study.architecture.money.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.opennuri.study.architecture.money.adapter.out.persistence.SpringDataChangingMoneyPersistence;
import org.opennuri.study.architecture.money.adapter.out.persistence.SpringDataMemberMoneyPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(value = "test")
@DisplayName(value = "고객 money 요청 테스트")
class RequestMoneyChangingControllerTest {

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
    private ObjectMapper objectMapper;
    @Test
    @Order(1)
    @DisplayName(value = "고객 money 증액 요청")
    void increaseMoney() throws Exception {

        ChangingMoneyRequest request = org.opennuri.study.architecture.money.adapter.in.web.ChangingMoneyRequest.builder()
                .membershipId("1")
                .moneyAmount(1000L)
                .build();

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/money/increase")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.membershipId").value("1"))
                .andExpect(jsonPath("$.amount").value("1000"))
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }
}