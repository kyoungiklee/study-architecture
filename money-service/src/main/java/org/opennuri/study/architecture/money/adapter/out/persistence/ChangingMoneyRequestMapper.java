package org.opennuri.study.architecture.money.adapter.out.persistence;

import org.opennuri.study.architecture.money.domain.ChangingMoneyRequest;
import org.springframework.stereotype.Component;

@Component
public class ChangingMoneyRequestMapper {
    public static ChangingMoneyRequest mapToChangingMoneyRequest(MoneyChangingRequestJpaEntity entity) {
        return ChangingMoneyRequest.getInstance(
                new ChangingMoneyRequest.ChangingMoneyRequestId(entity.getChangingMoneyRequestId())
                , new ChangingMoneyRequest.MembershipId(entity.getMembershipId())
                , new ChangingMoneyRequest.RequestType(entity.getRequestType())
                , new ChangingMoneyRequest.MoneyAmount(entity.getMoneyAmount())
                , new ChangingMoneyRequest.RequestStatus(entity.getRequestStatus())
                , new ChangingMoneyRequest.RequestDateTime(entity.getRequestDataTime())
                , new ChangingMoneyRequest.UUID(entity.getUuid()));
    }
}
