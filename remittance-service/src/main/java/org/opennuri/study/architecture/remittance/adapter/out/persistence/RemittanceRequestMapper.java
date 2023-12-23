package org.opennuri.study.architecture.remittance.adapter.out.persistence;

import org.opennuri.study.architecture.remittance.domain.RemittanceRequest;

/**
 * JpaEntity와 DomainEntity간의 매핑을 담당하는 Mapper
 */
public class RemittanceRequestMapper {

    public static RemittanceRequest mapToDomainEntity(RemittanceRequestJpaEntity entity) {
        return RemittanceRequest.from(
                new RemittanceRequest.RemittanceRequestId(entity.getRemittanceRequestId()),
                new RemittanceRequest.SenderId(entity.getSenderId()),
                new RemittanceRequest.ReceiverId(entity.getReceiverId()),
                new RemittanceRequest.ToBankName(entity.getToBankName()),
                new RemittanceRequest.ToAccountNumber(entity.getToAccountNumber()),
                new RemittanceRequest.RequestType(entity.getRequestType()),
                new RemittanceRequest.Amount(entity.getAmount()),
                new RemittanceRequest.Description(entity.getDescription()),
                new RemittanceRequest.RequestStatus(entity.getRequestStatus()),
                new RemittanceRequest.Uuid(entity.getUuid())
        );
    }
}
