package org.opennuri.study.architecture.money.adapter.out.persistence;

import org.opennuri.study.architecture.money.domain.MoneyChangingRequest;
import org.springframework.stereotype.Component;

@Component
public class ChangingMoneyRequestMapper {
    public static MoneyChangingRequest mapToChangingMoneyRequest(MoneyChangingRequestJpaEntity entity) {
        return MoneyChangingRequest.getInstance(
                new MoneyChangingRequest.ChangingMoneyRequestId(entity.getChangingMoneyRequestId())
                , new MoneyChangingRequest.MembershipId(entity.getMembershipId())
                , new MoneyChangingRequest.RequestType(entity.getRequestType())
                , new MoneyChangingRequest.MoneyAmount(entity.getMoneyAmount())
                , new MoneyChangingRequest.RequestStatus(entity.getRequestStatus())
                , new MoneyChangingRequest.RequestDateTime(entity.getRequestDataTime())
                , new MoneyChangingRequest.UUID(entity.getUuid()));
    }
}
