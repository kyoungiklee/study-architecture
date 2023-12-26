package org.opennuri.study.architecture.remittance.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.common.PersistenceAdapter;
import org.opennuri.study.architecture.remittance.application.port.in.FindRemittanceCommand;
import org.opennuri.study.architecture.remittance.application.port.out.FindRemittancePort;
import org.opennuri.study.architecture.remittance.application.port.out.RequestRemittancePort;
import org.opennuri.study.architecture.remittance.common.RemittanceStatus;
import org.opennuri.study.architecture.remittance.domain.RemittanceRequest;

import java.util.List;

@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public class RemittanceRequestPersistenceAdapter implements RequestRemittancePort, FindRemittancePort {

    private final RemittanceRequestRepository remittanceRequestRepository;
    @Override
    public List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command) {
        log.info("findRemittanceHistory");

        return null;
    }


    @Override
    public RemittanceRequest createRemittanceRequestHistory(RemittanceRequest.SenderId senderId
            , RemittanceRequest.ReceiverId receiverId
            , RemittanceRequest.ToBankName toBankName
            , RemittanceRequest.ToAccountNumber toAccountNumber
            , RemittanceRequest.RequestType requestType
            , RemittanceRequest.Amount amount
            , RemittanceRequest.Description description
            , RemittanceRequest.RequestStatus requestStatus
            , RemittanceRequest.Uuid uuid) {
        log.info("createRemittanceRequestHistory");
        RemittanceRequestJpaEntity entity = remittanceRequestRepository.save(RemittanceRequestJpaEntity.builder()
                .senderId(senderId.senderId())
                .receiverId(receiverId.receiverId())
                .toBankName(toBankName.toBankName())
                .toAccountNumber(toAccountNumber.toAccountNumber())
                .requestType(requestType.requestType())
                .amount(amount.amount())
                .description(description.description())
                .requestStatus(requestStatus.requestStatus())
                .uuid(uuid.uuid())
                .build());

        return RemittanceRequestMapper.mapToDomainEntity(entity);
    }

    @Override
    public RemittanceRequest saveRemittanceRequestHistory(RemittanceRequest remittanceRequest, RemittanceStatus remittanceStatus) {
        RemittanceRequestJpaEntity entity = remittanceRequestRepository.save(RemittanceRequestJpaEntity.builder()
                .senderId(remittanceRequest.getSenderId())
                .receiverId(remittanceRequest.getReceiverId())
                .toBankName(remittanceRequest.getToBankName())
                .toAccountNumber(remittanceRequest.getToAccountNumber())
                .requestType(remittanceRequest.getRequestType())
                .amount(remittanceRequest.getAmount())
                .description(remittanceRequest.getDescription())
                .requestStatus(remittanceStatus)
                .uuid(remittanceRequest.getUuid())
                .build());
        log.info("saveRemittanceRequestHistory");
        return RemittanceRequestMapper.mapToDomainEntity(entity);
    }
}
