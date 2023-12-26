package org.opennuri.study.architecture.remittance.common;

import jakarta.persistence.Embedded;
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
    //잔액 확인 완료
    MONEY_CHECK_COMPLETE("잔액 확인 완료"),
    //잔액 확인 실패
    MONEY_CHECK_FAIL("잔액 확인 실패"),
    //충전 요청 완료
    MONEY_RECHARGING_COMPLETE("충전 완료"),
    //충전 요청 실패
    MONEY_RECHARGING_FAIL("충전 실패"),
    //송금 완료
    COMPLETE("완료"),
    //송금 실패
    FAIL("실패");


    private final String value;
}
