package org.opennuri.study.architecture.remittance.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.opennuri.study.architecture.remittance.common.RemittanceStatus;
import org.opennuri.study.architecture.remittance.common.RemittanceType;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RemittanceRequest {
    //송금요청에 대한 정보를 담는 클래스
    private final Long remittanceRequestId; //송금요청 아이디
    private final Long senderId; //송금요청자 아이디
    private final Long receiverId; //송금요청 수신자 아이디
    private final String toBankName; //송금요청 은행 이름
    private final String toAccountNumber; // 송금요청 은행 계좌번호
    private final RemittanceType requestType; //송금요청 타입 (내부고객, 외부은행)
    private final Long amount; //송금요청 금액
    private final String description; //송금요청 설명
    private final RemittanceStatus requestStatus; //송금요청 상태 (요청, 완료, 실패)
    private final String uuid; //송금요청 uuid


    //RemittanceRequestVO  생성자
    public static RemittanceRequest from(RemittanceRequestId remittanceRequestId,
                                                       SenderId senderId,
                                                       ReceiverId receiverId,
                                                       ToBankName toBankName,
                                                       ToAccountNumber toAccountNumber,
                                                       RequestType requestType,
                                                       Amount amount,
                                                       Description description,
                                                       RequestStatus requestStatus,
                                                        Uuid uuid
                                            ) {
        return new RemittanceRequest(remittanceRequestId.remittanceRequestId(),
                senderId.senderId(),
                receiverId.receiverId(),
                toBankName.toBankName(),
                toAccountNumber.toAccountNumber(),
                requestType.requestType(),
                amount.amount(),
                description.description(),
                requestStatus.requestStatus(),
                uuid.uuid());
    }

    public record RemittanceRequestId(Long remittanceRequestId) {} //송금요청 아이디
    public record SenderId(Long senderId) {} //송금요청자 아이디
    public record ReceiverId(Long receiverId) {} //송금요청 수신자 아이디
    public record ToBankName(String toBankName) {} //송금요청 은행 이름
    public record ToAccountNumber(String toAccountNumber) {} //송금요청 은행 계좌번호
    public record RequestType(RemittanceType requestType) {} //송금요청 타입 (내부고객, 외부은행)
    public record Amount(Long amount) {} //송금요청 금액
    public record Description(String description) {} //송금요청 설명
    public record RequestStatus(RemittanceStatus requestStatus) {} //송금요청 상태 (요청, 완료, 실패)
    public record Uuid(String uuid) {} //송금요청 uuid

    @Override
    public String toString() {
        return "RemittanceRequestVO{" +
                "remittanceRequestId=" + remittanceRequestId +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", toBankName='" + toBankName + '\'' +
                ", toAccountNumber='" + toAccountNumber + '\'' +
                ", requestType=" + requestType +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", requestStatus=" + requestStatus +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
