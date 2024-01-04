package org.opennuri.study.architecture.remittance.application.port.in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.opennuri.study.architecture.common.SelfValidating;
import org.opennuri.study.architecture.remittance.common.RemittanceType;

@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class RemittanceSearchCommand extends SelfValidating<RemittanceSearchCommand> {

    @NotNull
    private Long senderId; //송금요청자 아이디
    private Long receiverId; //송금요청 수신자 아이디
    private String toBankName; //송금요청 은행 이름
    private String toAccountNumber; //송금요청 은행 계좌번호
    @NotNull
    private RemittanceType requestType; //송금요청 타입 (INTERNAL: 내부고객, EXTERNAL: 외부은행)
    @NotNull @Positive
    private Long amount; //송금요청 금액
    private String description; //송금요청 설명

    public RemittanceSearchCommand(Long senderId, Long receiverId, String toBankName, String toAccountNumber, RemittanceType requestType, Long amount, String description) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.toBankName = toBankName;
        this.toAccountNumber = toAccountNumber;
        this.requestType = requestType;
        this.amount = amount;
        this.description = description;
        this.validateSelf();
    }

    @Override
    public String toString() {
        return "RemittanceSearchCommand{" +
                "senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", toBankName='" + toBankName + '\'' +
                ", toAccountNumber='" + toAccountNumber + '\'' +
                ", requestType=" + requestType +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
