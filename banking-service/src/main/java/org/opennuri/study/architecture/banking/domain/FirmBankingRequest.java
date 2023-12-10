package org.opennuri.study.architecture.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import org.opennuri.study.architecture.banking.adapter.out.persistence.FirmBankingRequestStatus;
import org.opennuri.study.architecture.common.BaseDomainModel;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FirmBankingRequest extends BaseDomainModel {

    private Long firmBankingRequestId;
    private String membershipId;
    private String fromBankName;
    private String fromBankAccountNumber;
    private String toBankName;
    private String toBankAccountNumber;
    private Long moneyAmount;
    private String description;
    private FirmBankingRequestStatus requestStatus; //REQUESTED, APPROVED, REJECTED
    private String rejectReason;
    private String uuid;

    public static FirmBankingRequest generateFirmBankingRequest (
            FirmBankingRequest.FirmBankingRequestId firmBankingRequestId,
            FirmBankingRequest.MembershipId membershipId,
            FirmBankingRequest.FromBankName fromBankName,
            FirmBankingRequest.FromBankAccountNumber fromBankAccountNumber,
            FirmBankingRequest.ToBankName toBankName,
            FirmBankingRequest.ToBankAccountNumber toBankAccountNumber,
            FirmBankingRequest.MoneyAmount moneyAmount,
            FirmBankingRequest.RequestStatus requestStatus,
            FirmBankingRequest.Description description,
            FirmBankingRequest.RejectReason rejectReason,
            FirmBankingRequest.Uuid uuid
    ) {
        return new FirmBankingRequest(
                firmBankingRequestId.firmBankingRequestId
                , membershipId.membershipId
                , fromBankName.fromBankName
                , fromBankAccountNumber.fromBankAccountNumber
                , toBankName.toBankName
                , toBankAccountNumber.toBankAccountNumber
                , moneyAmount.moneyAmount
                , description.description
                , requestStatus.status
                , rejectReason.rejectReason
                , uuid.uuid
                );
    }

    @Value
    public static class FirmBankingRequestId {
        public FirmBankingRequestId(Long firmBankingRequestId) {
            this.firmBankingRequestId = firmBankingRequestId;
        }
        //@Value already marks non-static, package-local fields private. No need to add private modifier.
        Long firmBankingRequestId;
    }


    @Value
    public static class MembershipId {
        public MembershipId(String membershipId) {
            this.membershipId = membershipId;
        }

        String membershipId;
    }

    @Value
    public static class FromBankName {
        public FromBankName(String fromBankName) {
            this.fromBankName = fromBankName;
        }

        String fromBankName;
    }

    @Value
    public static class FromBankAccountNumber {
        public FromBankAccountNumber(String fromBankAccountNumber) {
            this.fromBankAccountNumber = fromBankAccountNumber;
        }

        String fromBankAccountNumber;
    }

    @Value
    public static class ToBankName {
        public ToBankName(String toBankName) {
            this.toBankName = toBankName;
        }

        String toBankName;
    }

    @Value
    public static class ToBankAccountNumber {
        public ToBankAccountNumber(String toBankAccountNumber) {
            this.toBankAccountNumber = toBankAccountNumber;
        }

        String toBankAccountNumber;
    }

    @Value
    public static class MoneyAmount {
        public MoneyAmount(Long moneyAmount) {
            this.moneyAmount = moneyAmount;
        }

        Long moneyAmount;
    }

    @Value
    public static class RequestStatus {
        public RequestStatus(FirmBankingRequestStatus status) {
            this.status = status;
        }

        FirmBankingRequestStatus status;
    }

    @Value
    public static class Description {
        public Description(String description) {
            this.description = description;
        }

        String description;
    }

    @Value
    public static class RejectReason {
        public RejectReason(String rejectReason) {
            this.rejectReason = rejectReason;
        }

        String rejectReason;
    }

    @Value
    public static class Uuid {
        public Uuid(String uuid) {
            this.uuid = uuid;
        }

        String uuid;
    }

    @Override
    public String toString() {
        return "FirmBankingRequest{" +
                "firmBankingRequestId=" + firmBankingRequestId +
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
