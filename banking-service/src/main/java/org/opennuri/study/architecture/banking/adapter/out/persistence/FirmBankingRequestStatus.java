package org.opennuri.study.architecture.banking.adapter.out.persistence;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FirmBankingRequestStatus {
    REQUESTED("요청"), APPROVED("승인"), REJECTED("거절");

    private final String status;
}
