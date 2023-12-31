package org.opennuri.study.architecture.banking.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.opennuri.study.architecture.common.BaseEntity;

@Entity
@Table(name = "firm_banking_request")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FirmBankingRequestedJpaEntity  extends BaseEntity {

    @Id
    @GeneratedValue
    private Long firmBankingRequestId;
    private Long membershipId;
    private String fromBankName;
    private String fromBankAccountNumber;
    private String toBankName;
    private String toBankAccountNumber;
    private Long moneyAmount;
    private String description;
    @Enumerated(value = EnumType.STRING)
    private FirmBankingRequestStatus requestStatus;
    private String rejectReason;
    private String uuid;
    private String aggregateId;

    public FirmBankingRequestedJpaEntity(
            Long membershipId
            , String fromBankName
            , String fromBankAccountNumber
            , String toBankName
            , String toBankAccountNumber
            , Long moneyAmount
            , String description
            , FirmBankingRequestStatus requestStatus
            , String rejectReason
            , String uuid
            , String aggregateId

    ) {

        this.membershipId = membershipId;
        this.fromBankName = fromBankName;
        this.fromBankAccountNumber = fromBankAccountNumber;
        this.toBankName = toBankName;
        this.toBankAccountNumber = toBankAccountNumber;
        this.moneyAmount = moneyAmount;
        this.description = description;
        this.requestStatus = requestStatus;
        this.rejectReason = rejectReason;
        this.uuid = uuid;
        this.aggregateId = aggregateId;

    }

    @Override
    public String toString() {
        return "FirmBankingRequestedJpaEntity{" +
                "firmBankingRequestId=" + firmBankingRequestId +
                ", membershipId=" + membershipId +
                ", fromBankName='" + fromBankName + '\'' +
                ", fromBankAccountNumber='" + fromBankAccountNumber + '\'' +
                ", toBankName='" + toBankName + '\'' +
                ", toBankAccountNumber='" + toBankAccountNumber + '\'' +
                ", moneyAmount=" + moneyAmount +
                ", description='" + description + '\'' +
                ", requestStatus=" + requestStatus +
                ", rejectReason='" + rejectReason + '\'' +
                ", uuid='" + uuid + '\'' +
                ", aggregateId='" + aggregateId + '\'' +
                '}';
    }
}
