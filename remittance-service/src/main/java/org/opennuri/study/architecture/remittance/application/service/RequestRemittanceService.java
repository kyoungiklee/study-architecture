package org.opennuri.study.architecture.remittance.application.service;

import lombok.RequiredArgsConstructor;
import org.opennuri.study.architecture.common.UseCase;
import org.opennuri.study.architecture.common.exception.BusinessCheckFailException;
import org.opennuri.study.architecture.remittance.application.port.in.RequestRemittanceCommand;
import org.opennuri.study.architecture.remittance.application.port.in.RequestRemittanceUseCase;
import org.opennuri.study.architecture.remittance.application.port.out.RequestRemittancePort;
import org.opennuri.study.architecture.remittance.application.port.out.membership.MembershipServicePort;
import org.opennuri.study.architecture.remittance.application.port.out.membership.MembershipInfo;
import org.opennuri.study.architecture.remittance.application.port.out.money.MoneyServicePort;
import org.opennuri.study.architecture.remittance.common.RemittanceStatus;
import org.opennuri.study.architecture.remittance.domain.RemittanceRequest;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class RequestRemittanceService implements RequestRemittanceUseCase {

    private final RequestRemittancePort requestRemittancePort;
    private final MembershipServicePort membershipServicePort;
    private final MoneyServicePort moneyServicePort;


    @Override
    public RemittanceRequest requestRemittance(RequestRemittanceCommand command) {

        //step1. remittanceRequest를 remittanceStatus가 REQUEST 인 상태로 저장한다..
        RemittanceRequest remittanceRequestHistory = requestRemittancePort.createRemittanceRequestHistory(
                new RemittanceRequest.SenderId(command.getSenderId()),
                new RemittanceRequest.ReceiverId(command.getReceiverId()),
                new RemittanceRequest.ToBankName(command.getToBankName()),
                new RemittanceRequest.ToAccountNumber(command.getToAccountNumber()),
                new RemittanceRequest.RequestType(command.getRequestType()),
                new RemittanceRequest.Amount(command.getAmount()),
                new RemittanceRequest.Description(command.getDescription()),
                new RemittanceRequest.RequestStatus(RemittanceStatus.REQUEST),
                new RemittanceRequest.Uuid(UUID.randomUUID().toString()
        ));


        //step2. senderId 정보로 고객 상태를 조회한다. (MemberShip service)
        MembershipInfo membershipInfo = membershipServicePort.getMembershipStatus(command.getSenderId());

        if(membershipInfo.isValid()) {
            //step2-1. membershipInfo가 유효하면 remittanceStatus를 MEMBERSHIP_CHECK_COMPLETE 변경후 저장한다.
            remittanceRequestHistory = requestRemittancePort.saveRemittanceRequestHistory(remittanceRequestHistory
                    , RemittanceStatus.MEMBERSHIP_CHECK_COMPLETE);
        } else {
            //step2-2. membershipInfo가 유효하지 않으면 remittanceStatus를 MEMBERSHIP_CHECK_FAIL 변경후 저장한다.
            remittanceRequestHistory = requestRemittancePort.saveRemittanceRequestHistory(remittanceRequestHistory
                    , RemittanceStatus.MEMBERSHIP_CHECK_FAIL);
            throw new BusinessCheckFailException("멤버십 상태가 유효하지 않습니다.");
        }


        //step3. senderId 정보로 MoneyInfo를 조회한다. (Money service)

        return remittanceRequestHistory;
    }
}
