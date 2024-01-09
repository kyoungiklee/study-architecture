package org.opennuri.study.architecture.banking.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.opennuri.study.architecture.banking.adapter.out.persistence.FirmBankingRequestStatus;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFirmBankingRequest {

    private String aggregateId;
    private FirmBankingRequestStatus status;
}
