package org.opennuri.study.architecture.banking.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.opennuri.study.architecture.common.BaseEntity;

@Entity
@Table(name = "firm_banking_request")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirmBankingRequestedJpaEntity  extends BaseEntity {

    @Id
    @GeneratedValue
    private Long firmBankingRequestId;
    private String membershipId;
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

    public FirmBankingRequestedJpaEntity(
            String membershipId
            , String fromBankName
            , String fromBankAccountNumber
            , String toBankName
            , String toBankAccountNumber
            , Long moneyAmount
            , String description
            , FirmBankingRequestStatus requestStatus
            , String rejectReason
            , String uuid) {

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

    }

    @Override
    public String toString() {
        return "FirmBankingRequestedJpsEntity{" +
                "firmBankingRequestId='" + firmBankingRequestId + '\'' +
                ", membershipId='" + membershipId + '\'' +
                ", fromBankName='" + fromBankName + '\'' +
                ", fromBankAccountNumber='" + fromBankAccountNumber + '\'' +
                ", toBankName='" + toBankName + '\'' +
                ", toBankAccountNumber='" + toBankAccountNumber + '\'' +
                ", moneyAmount=" + moneyAmount +
                ", description='" + description + '\'' +
                ", requestStatus=" + requestStatus +
                ", rejectReason='" + rejectReason + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
