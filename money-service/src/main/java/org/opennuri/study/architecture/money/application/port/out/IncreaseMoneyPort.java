package org.opennuri.study.architecture.money.application.port.out;

import org.opennuri.study.architecture.money.domain.ChangingMoneyRequest;

public interface IncreaseMoneyPort {

    ChangingMoneyRequest createChangeMoneyRequest(
            ChangingMoneyRequest.MembershipId membershipId,
            ChangingMoneyRequest.RequestType requestType,
            ChangingMoneyRequest.MoneyAmount moneyAmount,
            ChangingMoneyRequest.RequestStatus requestStatus,
            ChangingMoneyRequest.RequestDateTime requestDateTime,
            ChangingMoneyRequest.UUID uuid
    );

}
