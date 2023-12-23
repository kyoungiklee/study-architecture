package org.opennuri.study.architecture.remittance.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RemittanceType {
    //송금 타입 (내부고객 외부은행)
    INTERNAL("내부고객"),
    EXTERNAL("은행");

    private final String value;
}
