package org.opennuri.study.architecture.remittance.application.port.out;

import org.opennuri.study.architecture.remittance.common.RemittanceStatus;
import org.opennuri.study.architecture.remittance.domain.RemittanceRequest;

public interface RequestRemittancePort {
    RemittanceRequest createRemittanceRequestHistory(
            RemittanceRequest.SenderId senderId,
            RemittanceRequest.ReceiverId receiverId,
            RemittanceRequest.ToBankName toBankName,
            RemittanceRequest.ToAccountNumber toAccountNumber,
            RemittanceRequest.RequestType requestType,
            RemittanceRequest.Amount amount,
            RemittanceRequest.Description description,
            RemittanceRequest.RequestStatus requestStatus,
            RemittanceRequest.Uuid uuid
    );

    RemittanceRequest saveRemittanceRequestHistory(RemittanceRequest remittanceRequest
            , RemittanceStatus remittanceStatus);
}
