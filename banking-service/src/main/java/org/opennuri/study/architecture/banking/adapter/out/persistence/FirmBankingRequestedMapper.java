package org.opennuri.study.architecture.banking.adapter.out.persistence;

import org.opennuri.study.architecture.banking.domain.FirmBankingRequest;
import org.springframework.stereotype.Component;

@Component
public class FirmBankingRequestedMapper {

    FirmBankingRequest mapToDomainEntity(FirmBankingRequestedJpaEntity firmBankingRequestedJpaEntity) {
        return FirmBankingRequest.generateFirmBankingRequest(
                new FirmBankingRequest.FirmBankingRequestId(firmBankingRequestedJpaEntity.getFirmBankingRequestId()),
                new FirmBankingRequest.MembershipId(firmBankingRequestedJpaEntity.getMembershipId()),
                new FirmBankingRequest.FromBankName(firmBankingRequestedJpaEntity.getFromBankName()),
                new FirmBankingRequest.FromBankAccountNumber(firmBankingRequestedJpaEntity.getFromBankAccountNumber()),
                new FirmBankingRequest.ToBankName(firmBankingRequestedJpaEntity.getToBankName()),
                new FirmBankingRequest.ToBankAccountNumber(firmBankingRequestedJpaEntity.getToBankAccountNumber()),
                new FirmBankingRequest.MoneyAmount(firmBankingRequestedJpaEntity.getMoneyAmount()),
                new FirmBankingRequest.RequestStatus(firmBankingRequestedJpaEntity.getRequestStatus()),
                new FirmBankingRequest.Description(firmBankingRequestedJpaEntity.getDescription()),
                new FirmBankingRequest.RejectReason(firmBankingRequestedJpaEntity.getRejectReason()),
                new FirmBankingRequest.Uuid(firmBankingRequestedJpaEntity.getUuid())
        );
    }
}
