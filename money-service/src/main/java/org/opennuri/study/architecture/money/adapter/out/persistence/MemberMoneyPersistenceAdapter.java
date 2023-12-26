package org.opennuri.study.architecture.money.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.opennuri.study.architecture.common.PersistenceAdapter;
import org.opennuri.study.architecture.money.application.port.out.CreateMemberMoneyPort;
import org.opennuri.study.architecture.money.application.port.out.FindMemberMoneyPort;
import org.opennuri.study.architecture.money.application.port.out.RechargingMoneyPort;
import org.opennuri.study.architecture.money.domain.MemberMoney;
import org.opennuri.study.architecture.money.domain.MoneyChangingRequest;

@PersistenceAdapter
@RequiredArgsConstructor
public class MemberMoneyPersistenceAdapter implements FindMemberMoneyPort, CreateMemberMoneyPort{
    private final SpringDataMemberMoneyPersistence memberMoneyPersistence;

    @Override
    public MemberMoney findMemberMoney(Long membershipId) {
        MemberMoneyJpaEntity entityByMembershipId = memberMoneyPersistence.findByMembershipId(membershipId);
        if(entityByMembershipId == null) {
            return null;
        }
        return MemberMoneyMapper.mapToMemberMoney(entityByMembershipId);
    }

    @Override
    public MemberMoney createMemberMoney(MemberMoney.MembershipId membershipId, MemberMoney.MoneyAmount moneyAmount) {
        MemberMoneyJpaEntity entity = memberMoneyPersistence.save(new MemberMoneyJpaEntity(
                membershipId.membershipId()
                , moneyAmount.moneyAmount()
        ));
        return MemberMoneyMapper.mapToMemberMoney(entity);
    }
}
