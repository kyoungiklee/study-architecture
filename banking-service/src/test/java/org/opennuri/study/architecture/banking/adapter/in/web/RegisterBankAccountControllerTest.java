package org.opennuri.study.architecture.banking.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.opennuri.study.architecture.banking.adapter.out.persistence.SpringDataRegisteredBankAccountRepository;
import org.opennuri.study.architecture.banking.appication.service.BankAccountFindService;
import org.opennuri.study.architecture.banking.domain.RegisteredBankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(value = "test")
@DisplayName(value = "Controller: RegisterBankAccountControllerTest(계좌 등록 테스트)")
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

    @Autowired
    private BankAccountFindService bankAccountFindService;

    @Test
    @Order(1)
    @DisplayName(value = "고객은 연결계좌를 등록한다")
    void registerBankAccount() throws Exception {
        RegisterBankAccountRequest request = RegisterBankAccountRequest.builder()
                .bankName("simple bank")
                .bankAccountNumber("123-45678-67890")
                .membershipId("1")
                .validLinkedStatus(true)
                .build();


        mockMvc.perform(MockMvcRequestBuilders.post("/banking/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Optional<RegisteredBankAccount> bankAccount =  bankAccountFindService.findBankAccount(1L);
        assertThat(bankAccount.isPresent()).isTrue();
        assertThat(bankAccount.get().getBankName()).isEqualTo("simple bank");
        assertThat(bankAccount.get().getBankAccountNumber()).isEqualTo("123-45678-67890");
        assertThat(bankAccount.get().getMembershipId()).isEqualTo("1");
        assertThat(bankAccount.get().isValidLinkedStatus()).isEqualTo(true);
    }
}