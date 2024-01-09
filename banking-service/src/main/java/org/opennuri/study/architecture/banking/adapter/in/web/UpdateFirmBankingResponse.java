package org.opennuri.study.architecture.banking.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.opennuri.study.architecture.banking.adapter.out.persistence.FirmBankingRequestStatus;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class UpdateFirmBankingResponse {
    private String aggregateId;
    private FirmBankingRequestStatus status;
    private String description;

}
