package org.opennuri.study.architecture.remittance.adapter.out.service.banking;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.opennuri.study.architecture.remittance.application.port.out.banking.BankingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Adapter: BankingServiceAdapterTest")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class BankingServiceAdapterTest {
    @Autowired
    private BankingServiceAdapter bankingServiceAdapter;

    @Test
    @DisplayName("뱅킹서비스로 고객의 연결계좌 정보를 조회한다.(remmitance -> banking)")
    void getMembershipBankingInfo() {
        //given
        String MembershipId = "1";
        //when
        BankingInfo membershipBankingInfo = bankingServiceAdapter.getMembershipBankingInfo("1");
        //then
        assertThat(membershipBankingInfo).isNotNull();
        assertThat(membershipBankingInfo.getMembershipId()).isEqualTo(MembershipId);
    }

    @Test
    @DisplayName("뱅킹서비스로 펌뱅킹을 요청한다.(remmitance -> banking)")
    void requestFirmBanking() {
        //given
        FirmBankingRequest firmBankingRequest = FirmBankingRequest.builder()
                .fromBankName("신한은행")
                .fromBankAccountNumber("1234567890")
                .toBankName("국민은행")
                .toBankAccountNumber("0987654321")
                .moneyAmount(10000L)
                .description("테스트")
                .membershipId("1")
                .build();
        //when
        boolean result = bankingServiceAdapter.requestFirmBanking(firmBankingRequest);
        //then
        assertThat(result).isTrue();
    }
}