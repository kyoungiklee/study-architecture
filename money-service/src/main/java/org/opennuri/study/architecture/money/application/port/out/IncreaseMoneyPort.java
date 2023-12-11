package org.opennuri.study.architecture.money.application.port.out;

import org.opennuri.study.architecture.money.domain.ChangingMoneyRequest;
import org.opennuri.study.architecture.money.domain.MemberMoney;

public interface IncreaseMoneyPort {

    // 증액요청 생성
    ChangingMoneyRequest createChangeMoneyRequest(
            ChangingMoneyRequest.MembershipId membershipId,
            ChangingMoneyRequest.RequestType requestType,
            ChangingMoneyRequest.MoneyAmount moneyAmount,
            ChangingMoneyRequest.RequestStatus requestStatus,
            ChangingMoneyRequest.RequestDateTime requestDateTime,
            ChangingMoneyRequest.UUID uuid
    );

    /**
     * 멤버십에 Money를 증액한다.
     * @param membershipId 멤버십 ID
     * @param moneyAmount 증액할 금액
     * @return  증액된 멤버십 Money
     */
    MemberMoney increaseMoney(
            MemberMoney.MembershipId membershipId
            , MemberMoney.MoneyAmount moneyAmount
    );

}
