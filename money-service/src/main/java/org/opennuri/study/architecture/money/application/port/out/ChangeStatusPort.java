package org.opennuri.study.architecture.money.application.port.out;

import org.opennuri.study.architecture.money.domain.ChangingMoneyRequest;
import org.opennuri.study.architecture.money.domain.ChangingMoneyRequestStatus;

public interface ChangeStatusPort {
    // 증액요청 상태를 완료로 변경
    ChangingMoneyRequest changeRequestStatus(String uuid, ChangingMoneyRequestStatus status);
}
