package org.opennuri.study.architecture.banking.adapter.out.persistence;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.opennuri.study.architecture.common.BaseEntity;

@Entity
@Table(name = "registered_bank_account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredBankAccountJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue
    private Long registeredBankAccountId;
    private Long membershipId;
    private String bankName;
    private String bankAccountNumber;
    private boolean validLinkedStatus;
    private String aggregateId;

    public RegisteredBankAccountJpaEntity(
            Long membershipId
            , String bankName
            , String bankAccountNumber
            , boolean validLinkedStatus
            , String aggregateId
    ) {

        this.membershipId = membershipId;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.validLinkedStatus = validLinkedStatus;
        this.aggregateId = aggregateId;

    }

    @Override
    public String toString() {
        return "RegisteredBankAccountJpaEntity{" +
                "registeredBankAccountId=" + registeredBankAccountId +
                ", membershipId=" + membershipId +
                ", bankName='" + bankName + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", validLinkedStatus=" + validLinkedStatus +
                ", aggregateId='" + aggregateId + '\'' +
                '}';
    }
}
