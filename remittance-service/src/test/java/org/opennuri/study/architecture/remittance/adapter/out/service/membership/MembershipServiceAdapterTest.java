package org.opennuri.study.architecture.remittance.adapter.out.service.membership;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.opennuri.study.architecture.remittance.application.port.out.membership.MembershipInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles(value = "test")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@DisplayName(value = "MembershipServiceAdapter 테스트")
class MembershipServiceAdapterTest {

    @Autowired
    private MembershipServiceAdapter membershipServiceAdapter;

    @Test
    @Order(1)
    @DisplayName(value = "멤버십 상태를 조회한다.")
    void getMembershipStatus() {
        MembershipInfo membershipInfo = membershipServiceAdapter.getMembershipStatus("1");

        log.info("membershipInfo: {}", membershipInfo);

        assertThat(membershipInfo).isNotNull();
        assertThat(membershipInfo.getMembershipId()).isEqualTo("1");    // 멤버십 아이디
        assertThat(membershipInfo.isValid()).isEqualTo(true); // 멤버십 상태
    }
}