package org.opennuri.study.architecture.remittance.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RemittanceStatus {
    //송금요청 상태 (요청, 완료, 실패)
    REQUEST("요청"),
    //멤버 상태 확인 완료
    MEMBERSHIP_CHECK_COMPLETE("멤버십 확인 완료"),
    //멤버 상태 확인 실패
    MEMBERSHIP_CHECK_FAIL("멤버십 확인 실패"),

    COMPLETE("완료"),
    FAIL("실패");

    private final String value;
}
