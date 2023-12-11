package org.opennuri.study.architecture.money.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.opennuri.study.architecture.common.PersistenceAdapter;
import org.opennuri.study.architecture.money.application.port.out.ChangeStatusPort;
import org.opennuri.study.architecture.money.application.port.out.IncreaseMoneyPort;
import org.opennuri.study.architecture.money.domain.ChangingMoneyRequest;
import org.opennuri.study.architecture.money.domain.ChangingMoneyRequestStatus;
import org.opennuri.study.architecture.money.domain.MemberMoney;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 증액요청 저장소 어댑터
 * - 증액요청 저장소를 사용하는 포트 구현, 멤버십 Money 저장소를 사용하는 포트 구현
 */
@PersistenceAdapter
@RequiredArgsConstructor
public class MoneyChangingRequestPersistenceAdapter implements IncreaseMoneyPort, ChangeStatusPort {

    private final SpringDataChangingMoneyPersistence changingMoneyPersistence; // 증액요청 저장소
    private final SpringDataMemberMoneyPersistence memberMoneyPersistence; // 멤버십 Money 저장소

    /**
     * 증액요청 생성
     * @param membershipId 멤버십 ID
     * @param requestType 증액요청 타입
     * @param moneyAmount 증액요청 금액
     * @param requestStatus 증액요청 상태
     * @param requestDateTime 증액요청 일시
     * @param uuid 증액요청 UUID
     * @return 생성된 증액요청
     */
    @Override
    public ChangingMoneyRequest createChangeMoneyRequest(
            ChangingMoneyRequest.MembershipId membershipId
            , ChangingMoneyRequest.RequestType requestType
            , ChangingMoneyRequest.MoneyAmount moneyAmount
            , ChangingMoneyRequest.RequestStatus requestStatus
            , ChangingMoneyRequest.RequestDateTime requestDateTime
            , ChangingMoneyRequest.UUID uuid) {

        MoneyChangingRequestJpaEntity savedEntity = changingMoneyPersistence.save(new MoneyChangingRequestJpaEntity(
                membershipId.membershipId()
                , requestType.requestType()
                , moneyAmount.moneyAmount()
                , requestStatus.requestStatus()
                , requestDateTime.requestDateTime()
                , uuid.uuid()
        ));

        return ChangingMoneyRequestMapper.mapToChangingMoneyRequest(savedEntity);
    }

    /**
     * 멤버십에 Money를 증액한다.
     * @param membershipId 멤버십 ID
     * @param moneyAmount 증액할 금액
     * @return  증액된 멤버십 Money
     */
    @Override
    public MemberMoney increaseMoney(MemberMoney.MembershipId membershipId, MemberMoney.MoneyAmount moneyAmount) {
        //1. 멤버십 조회
        MemberMoneyJpaEntity findByMembershipEntity = memberMoneyPersistence.findByMembershipId(membershipId.value());
        //2. 멤버가 없는 경우 멤버를 생성하고 증액
        if(findByMembershipEntity == null){
            MemberMoneyJpaEntity savedEntity = memberMoneyPersistence.save(new MemberMoneyJpaEntity(
                    membershipId.value()
                    , moneyAmount.value()
            ));
            return MemberMoneyMapper.mapToMemberMoney(savedEntity);
        }
        //2. 멤버십 Money 증액
        findByMembershipEntity.increaseMoney(moneyAmount.value());
        //3. 멤버십 Money 저장
        MemberMoneyJpaEntity savedEntity = memberMoneyPersistence.save(findByMembershipEntity);
        //4. 증액된 멤버십 Money 리턴
        return MemberMoneyMapper.mapToMemberMoney(savedEntity);
    }

    /**
     * 증액요청 상태를 완료로 변경
     * @param uuid 증액요청 UUID
     * @param status  완료로 변경할 상태
     * @return  변경된 증액요청
     */
    @Override
    public ChangingMoneyRequest changeRequestStatus(String uuid, ChangingMoneyRequestStatus status) {
        //1. uuid로 증액요청 조회
        Optional<MoneyChangingRequestJpaEntity> optional = changingMoneyPersistence.findByUuid(uuid);
        if(optional.isEmpty()){
            throw new IllegalArgumentException("uuid not found");
        }
        MoneyChangingRequestJpaEntity entity = optional.get();
        //2. 증액요청 상태를 완료로 변경
        entity.setRequestStatus(status);
        //3. 변경된 증액요청 리턴
        return ChangingMoneyRequestMapper.mapToChangingMoneyRequest(entity);
    }
}
