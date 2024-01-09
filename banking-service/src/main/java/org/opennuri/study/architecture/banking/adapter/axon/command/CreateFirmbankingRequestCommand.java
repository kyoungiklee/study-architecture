package org.opennuri.study.architecture.banking.adapter.axon.command;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.opennuri.study.architecture.common.SelfValidating;

@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class CreateFirmbankingRequestCommand extends SelfValidating<CreateFirmbankingRequestCommand> {

        private String toBankName;
        private String toBankAccountNumber;

        private String fromBankName;
        private String fromBankAccountNumber;

        private Long amount;

        public CreateFirmbankingRequestCommand(String toBankName, String toBankAccountNumber, String fromBankName
                , String fromBankAccountNumber, Long amount) {
            this.toBankName = toBankName;
            this.toBankAccountNumber = toBankAccountNumber;
            this.fromBankName = fromBankName;
            this.fromBankAccountNumber = fromBankAccountNumber;
            this.amount = amount;
            this.validateSelf();
        }
}
