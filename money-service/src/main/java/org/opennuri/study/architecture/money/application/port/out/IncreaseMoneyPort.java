package org.opennuri.study.architecture.money.application.port.out;

import org.opennuri.study.architecture.money.domain.MoneyChangingRequest;
import org.opennuri.study.architecture.money.domain.MemberMoney;

public interface IncreaseMoneyPort {

    // 증액요청 생성
    MoneyChangingRequest createChangeMoneyRequest(
            MoneyChangingRequest.MembershipId membershipId,
            MoneyChangingRequest.RequestType requestType,
            MoneyChangingRequest.MoneyAmount moneyAmount,
            MoneyChangingRequest.RequestStatus requestStatus,
            MoneyChangingRequest.RequestDateTime requestDateTime,
            MoneyChangingRequest.UUID uuid
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
