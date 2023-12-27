package org.opennuri.study.architecture.banking.adapter.in.web;

import org.junit.jupiter.api.*;
import org.opennuri.study.architecture.banking.adapter.out.persistence.SpringDataRegisteredBankAccountRepository;
import org.opennuri.study.architecture.banking.appication.port.in.RegisterBankAccountCommand;
import org.opennuri.study.architecture.banking.appication.service.BankAccountRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@DisplayName("FindBankAccountController 테스트")
class FindBankAccountControllerTest {

    @AfterEach
    void tearDown() {
    }

    @Autowired
    private SpringDataRegisteredBankAccountRepository springDataRegisteredBankAccountRepository;

    @BeforeEach()
    void setUp() {
        springDataRegisteredBankAccountRepository.deleteAll();
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BankAccountRegisterService bankAccountRegisterService;
    @Test
    @DisplayName("고객 계좌 조회")
    void findBankAccount() throws Exception {
        // given
        bankAccountRegisterService.registerBankAccount(RegisterBankAccountCommand.builder()
                .membershipId("1")
                .bankName("abc bank")
                .bankAccountNumber("1111-2222-3333-4444")
                .validLinkedStatus(true)
                .build());

        // when //then
        mockMvc.perform(get("/banking/account/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.membershipId").value("1"))
                .andExpect(jsonPath("$.bankName").value("abc bank"))
                .andExpect(jsonPath("$.bankAccountNumber").value("1111-2222-3333-4444"))
                .andExpect(jsonPath("$.validLinkedStatus").value(true));
    }

}