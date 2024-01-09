package org.opennuri.study.architecture.money.adapter.axon.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RechargingRequestCreatedEvent {
    //충전 요청이 생성되었다는 이벤트
    private Long rechargingRequestId;
    private Long membershipId;
    private String bankName;
    private String bankAccountNumber;
    private Long amount;
    private String registeredBankAccountId;
}
