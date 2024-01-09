package org.opennuri.study.architecture.banking.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.opennuri.study.architecture.banking.appication.port.out.RequestFirmBankingPort;
import org.opennuri.study.architecture.banking.domain.FirmBankingRequest;
import org.opennuri.study.architecture.common.PersistenceAdapter;

@PersistenceAdapter
@RequiredArgsConstructor
public class FirmBankingRequestPersistenceAdapter implements RequestFirmBankingPort {

    private final SpringDataFirmBankingRequestRepository repository;
    private final FirmBankingRequestedMapper firmBankingRequestedMapper;


    @Override
    public FirmBankingRequest createFirmBankingRequest(
            FirmBankingRequest.MembershipId membershipId
            , FirmBankingRequest.FromBankName fromBankName
            , FirmBankingRequest.FromBankAccountNumber fromBankAccountNumber
            , FirmBankingRequest.ToBankName toBankName
            , FirmBankingRequest.ToBankAccountNumber toBankAccountNumber
            , FirmBankingRequest.MoneyAmount moneyAmount
            , FirmBankingRequest.RequestStatus requestStatus
            , FirmBankingRequest.RejectReason rejectedReason
            , FirmBankingRequest.Description description
            , FirmBankingRequest.Uuid uuid
            , FirmBankingRequest.AggregateId aggregateId) {

        FirmBankingRequestedJpaEntity savedEntity = repository.save(
                new FirmBankingRequestedJpaEntity(
                        membershipId.getMembershipId()
                        , fromBankName.getFromBankName()
                        , fromBankAccountNumber.getFromBankAccountNumber()
                        , toBankName.getToBankName()
                        , toBankAccountNumber.getToBankAccountNumber()
                        , moneyAmount.getMoneyAmount()
                        , description.getDescription()
                        , requestStatus.getStatus()
                        , rejectedReason.getRejectReason()
                        , uuid.getUuid()
                        , aggregateId.getAggregateId()
                        )
        );
        return firmBankingRequestedMapper.mapToDomainEntity(savedEntity);
    }

    @Override
    public FirmBankingRequest updateFirmBankingRequestStatus(String uuid, FirmBankingRequestStatus firmBankingRequestStatus) {
        FirmBankingRequestedJpaEntity firmBankingRequestedJpaEntity = repository.findByUuid(uuid);
        firmBankingRequestedJpaEntity.setRequestStatus(firmBankingRequestStatus);
        repository.save(firmBankingRequestedJpaEntity);

        return firmBankingRequestedMapper.mapToDomainEntity(firmBankingRequestedJpaEntity);
    }

    @Override
    public FirmBankingRequest updateFirmBankingRequestStatusByEvent(String aggregateId, FirmBankingRequestStatus firmBankingRequestStatus) {
        FirmBankingRequestedJpaEntity firmBankingRequestedJpaEntity = repository.findByAggregateId(aggregateId);
        return firmBankingRequestedMapper.mapToDomainEntity(firmBankingRequestedJpaEntity);
    }
}
