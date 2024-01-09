package org.opennuri.study.architecture.banking.appication.port.out;

import org.opennuri.study.architecture.banking.domain.FirmBankingRequest;
import org.opennuri.study.architecture.banking.adapter.out.persistence.FirmBankingRequestStatus;

public interface RequestFirmBankingPort {

    FirmBankingRequest createFirmBankingRequest(
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
            , FirmBankingRequest.AggregateId aggregateId
    );

    FirmBankingRequest updateFirmBankingRequestStatus(String uuid, FirmBankingRequestStatus firmBankingRequestStatus);

    FirmBankingRequest updateFirmBankingRequestStatusByEvent(String aggregateId, FirmBankingRequestStatus firmBankingRequestStatus);
}
