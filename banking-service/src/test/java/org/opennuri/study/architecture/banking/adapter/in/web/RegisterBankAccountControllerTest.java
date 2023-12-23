package org.opennuri.study.architecture.banking.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.opennuri.study.architecture.banking.adapter.out.persistence.SpringDataRegisteredBankAccountRepository;
import org.opennuri.study.architecture.banking.domain.RegisteredBankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(value = "test")
@DisplayName(value = "계좌 등록 테스트")
class RegisterBankAccountControllerTest {
    @Autowired
    private SpringDataRegisteredBankAccountRepository springDataRegisteredBankAccountRepository;

    @BeforeEach()
    void setUp() {
        springDataRegisteredBankAccountRepository.deleteAll();
    }


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @Order(1)
    @DisplayName(value = "계좌 등록")
    void registerBankAccount() throws Exception {
        RegisterBankAccountRequest request = RegisterBankAccountRequest.builder()
                .bankName("simple bank")
                .bankAccountNumber("123-45678-67890")
                .membershipId("1")
                .validLinkedStatus(true)
                .build();

        RegisteredBankAccount registeredBankAccount = RegisteredBankAccount.generateRegisteredBankAccount(
                new RegisteredBankAccount.RegisteredBankAccountId("1")
                , new RegisteredBankAccount.MembershipId("1")
                , new RegisteredBankAccount.BankName("simple bank")
                , new RegisteredBankAccount.BankAccountNumber("123-45678-67890")
                , new RegisteredBankAccount.ValidLinkedStatus(true)
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/banking/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(registeredBankAccount)));
    }
}