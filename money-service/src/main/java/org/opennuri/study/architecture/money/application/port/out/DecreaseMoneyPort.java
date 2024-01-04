package org.opennuri.study.architecture.money.application.port.out;

import org.opennuri.study.architecture.money.domain.MemberMoney;
import org.opennuri.study.architecture.money.domain.MoneyChangingRequest;

public interface DecreaseMoneyPort {

    MoneyChangingRequest createChangeMoneyRequest(
            MoneyChangingRequest.MembershipId membershipId,
            MoneyChangingRequest.RequestType requestType,
            MoneyChangingRequest.MoneyAmount moneyAmount,
            MoneyChangingRequest.RequestStatus requestStatus,
            MoneyChangingRequest.RequestDateTime requestDateTime,
            MoneyChangingRequest.UUID uuid
    );

    MemberMoney decreaseMoney(
            MemberMoney.MembershipId membershipId,
            Long moneyAmount
    );

}
