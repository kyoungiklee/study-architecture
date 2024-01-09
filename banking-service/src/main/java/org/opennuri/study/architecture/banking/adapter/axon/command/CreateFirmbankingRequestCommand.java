package org.opennuri.study.architecture.banking.adapter.axon.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.opennuri.study.architecture.common.SelfValidating;

@NoArgsConstructor
@Data
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

    public static CreateFirmbankingRequestCommandBuilder builder() {
        return new CreateFirmbankingRequestCommandBuilder();
    }

    // Builder pattern implementation
    public static class CreateFirmbankingRequestCommandBuilder {
        private String toBankName;
        private String toBankAccountNumber;
        private String fromBankName;
        private String fromBankAccountNumber;
        private Long amount;

        public CreateFirmbankingRequestCommandBuilder toBankName(String toBankName) {
            this.toBankName = toBankName;
            return this;
        }

        public CreateFirmbankingRequestCommandBuilder toBankAccountNumber(String toBankAccountNumber) {
            this.toBankAccountNumber = toBankAccountNumber;
            return this;
        }

        public CreateFirmbankingRequestCommandBuilder fromBankName(String fromBankName) {
            this.fromBankName = fromBankName;
            return this;
        }

        public CreateFirmbankingRequestCommandBuilder fromBankAccountNumber(String fromBankAccountNumber) {
            this.fromBankAccountNumber = fromBankAccountNumber;
            return this;
        }

        public CreateFirmbankingRequestCommandBuilder amount(Long amount) {
            this.amount = amount;
            return this;
        }

        public CreateFirmbankingRequestCommand build() {
            return new CreateFirmbankingRequestCommand(toBankName, toBankAccountNumber, fromBankName, fromBankAccountNumber, amount);
        }
    }
}
