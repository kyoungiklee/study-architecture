package org.opennuri.study.architecture.money.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChangingMoneyRequestStatus {
    REQUESTED("요청"), // 요청됨
    SUCCESS("성공"), // 성공
    //고객확인완료
    MEMBERSHIP_CHECKED("고객확인완료"), // 고객확인완료
    //연결계좌확인완료
    ACCOUNT_CHECKED("연결계좌확인완료"), // 연결계좌확인완료
    //펌뱅킹계좌이체완료
    TRANSFER_FIRM_BANKING_COMPLETE("펌뱅킹계좌이체완료"), // 펌뱅킹계좌이체완료
    //고객확인실패
    MEMBERSHIP_CHECK_FAILED("고객확인실패"), // 고객확인실패
    //연결계좌확인실패
    ACCOUNT_CHECK_FAILED("연결계좌확인실패"), // 연결계좌확인실패
    //펌뱅킹계좌이체실패
    TRANSFER_FIRM_BANKING_FAILED("펌뱅킹계좌이체실패"), // 펌뱅킹계좌이체실패
    FAILED("실패"); // 실패

    private final String description;
}
