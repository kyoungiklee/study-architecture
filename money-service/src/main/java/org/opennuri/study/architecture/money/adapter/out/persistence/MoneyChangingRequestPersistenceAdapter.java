package org.opennuri.study.architecture.money.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.opennuri.study.architecture.common.PersistenceAdapter;
import org.opennuri.study.architecture.money.application.port.out.IncreaseMoneyPort;
import org.opennuri.study.architecture.money.domain.ChangingMoneyRequest;
import org.opennuri.study.architecture.money.domain.MemberMoney;

@PersistenceAdapter
@RequiredArgsConstructor
public class MoneyChangingRequestPersistenceAdapter implements IncreaseMoneyPort {

    private final SpringDataMoneyChangingRequestRepository repository;

    @Override
    public ChangingMoneyRequest createChangeMoneyRequest(
            ChangingMoneyRequest.MembershipId membershipId
            , ChangingMoneyRequest.RequestType requestType
            , ChangingMoneyRequest.MoneyAmount moneyAmount
            , ChangingMoneyRequest.RequestStatus requestStatus
            , ChangingMoneyRequest.RequestDateTime requestDateTime
            , ChangingMoneyRequest.UUID uuid) {
        MoneyChangingRequestJpaEntity savedEntity = repository.save(new MoneyChangingRequestJpaEntity(
                membershipId.membershipId()
                , requestType.requestType()
                , moneyAmount.moneyAmount()
                , requestStatus.requestStatus()
                , requestDateTime.requestDateTime()
                , uuid.uuid()
        ));
        return null;
    }

    @Override
    public MemberMoney requestIncreaseMoney() {
        //todo 멤버머니 잔액을 증액
        return null;
    }
}
