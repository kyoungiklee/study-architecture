package org.opennuri.study.architecture.banking.appication.port.in;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.opennuri.study.architecture.banking.adapter.out.persistence.FirmBankingRequestStatus;
import org.opennuri.study.architecture.common.SelfValidating;


@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class UpdateFirmBankingCommand extends SelfValidating<UpdateFirmBankingCommand> {

        private String aggregateId;
        private FirmBankingRequestStatus status;

        public UpdateFirmBankingCommand(String aggregateId, FirmBankingRequestStatus status) {
            this.aggregateId = aggregateId;
            this.status = status;
            this.validateSelf();
        }
}
