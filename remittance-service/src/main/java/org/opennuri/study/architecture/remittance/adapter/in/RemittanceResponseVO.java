package org.opennuri.study.architecture.remittance.adapter.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.opennuri.study.architecture.remittance.common.RemittanceType;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RemittanceResponseVO {

    private Long remittanceId; //송금요청 아이디
    private Long senderId; //송금요청자 아이디
    private Long receiverId; //송금요청 수신자 아이디
    private String toBankName; //송금요청 은행 이름
    private String toAccountNumber; //송금요청 은행 계좌번호
    private RemittanceType requestType; //송금요청 타입 (INTERNAL: 내부고객, EXTERNAL: 외부은행)
    private Long amount; //송금요청 금액
    private String description; //송금요청 설명
    private String message; //처리결과 메시지

    @Override
    public String toString() {
        return "RemittanceResponseVO{" +
                "remittanceId=" + remittanceId +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", toBankName='" + toBankName + '\'' +
                ", toAccountNumber='" + toAccountNumber + '\'' +
                ", requestType=" + requestType +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
