package org.opennuri.study.architecture.money.adapter.out.persistence;

import org.opennuri.study.architecture.common.PersistenceAdapter;
import org.opennuri.study.architecture.money.application.port.out.IncreaseMoneyPort;
import org.opennuri.study.architecture.money.domain.ChangingMoneyRequest;

@PersistenceAdapter
public class MoneyChangingRequestPersistenceAdapter implements IncreaseMoneyPort {
    @Override
    public ChangingMoneyRequest createChangeMoneyRequest(ChangingMoneyRequest.MembershipId membershipId, ChangingMoneyRequest.RequestType requestType, ChangingMoneyRequest.MoneyAmount moneyAmount, ChangingMoneyRequest.RequestStatus requestStatus, ChangingMoneyRequest.RequestDateTime requestDateTime, ChangingMoneyRequest.UUID uuid) {
        return null;
    }
}
