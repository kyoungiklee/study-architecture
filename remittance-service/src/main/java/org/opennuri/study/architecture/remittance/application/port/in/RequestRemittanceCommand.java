package org.opennuri.study.architecture.remittance.application.port.in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.opennuri.study.architecture.common.SelfValidating;
import org.opennuri.study.architecture.remittance.common.RemittanceStatus;
import org.opennuri.study.architecture.remittance.common.RemittanceType;

@Data
@EqualsAndHashCode(callSuper = false)
public class RequestRemittanceCommand extends SelfValidating<RequestRemittanceCommand> {
    @NotNull
    private Long senderId; //송금요청자 아이디
    private Long receiverId; //송금요청 수신자 아이디
    private String toBankName; //송금요청 은행 이름
    private String toAccountNumber; //송금요청 은행 계좌번호
    private RemittanceType requestType; //송금요청 타입 (내부고객, 외부은행)

    @NotNull @Positive
    private Long amount; //송금요청 금액
    private String description; //송금요청 설명
    private RemittanceStatus requestStatus; //송금요청 상태 (요청, 완료, 실패)


    // RemittanceRequestVO  생성자
    // RequestRemittanceCommandBuilder의 build() 메서드를 통해 생성자를 호출할 수 있으며
    // 생셩자 호출 시 validation을 수행한다.
    private RequestRemittanceCommand(@NotNull Long senderId, Long receiverId, String toBankName, String toAccountNumber, RemittanceType requestType, @NotNull @Positive Long amount, String description, RemittanceStatus requestStatus) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.toBankName = toBankName;
        this.toAccountNumber = toAccountNumber;
        this.requestType = requestType;
        this.amount = amount;
        this.description = description;
        this.requestStatus = requestStatus;
        this.validateSelf(); //validation 수행
    }

    public static void main(String[] args) {
        RequestRemittanceCommand requestRemittanceCommand = RequestRemittanceCommand.builder()
                .senderId(1L)
                .receiverId(2L)
                .toBankName("toBankName")
                .toAccountNumber("toAccountNumber")
                .requestType(RemittanceType.INTERNAL)
                .amount(1000L)
                .description("description")
                .requestStatus(RemittanceStatus.REQUEST)
                .build();
        System.out.println(requestRemittanceCommand);
    }

    public static RequestRemittanceCommandBuilder builder() {
        return new RequestRemittanceCommandBuilder();
    }

    public static class RequestRemittanceCommandBuilder {
        private @NotNull Long senderId;
        private Long receiverId;
        private String toBankName;
        private String toAccountNumber;
        private RemittanceType requestType;
        private @NotNull @Positive Long amount;
        private String description;
        private RemittanceStatus requestStatus;

        RequestRemittanceCommandBuilder() {
        }

        public RequestRemittanceCommandBuilder senderId(@NotNull Long senderId) {
            this.senderId = senderId;
            return this;
        }

        public RequestRemittanceCommandBuilder receiverId(Long receiverId) {
            this.receiverId = receiverId;
            return this;
        }

        public RequestRemittanceCommandBuilder toBankName(String toBankName) {
            this.toBankName = toBankName;
            return this;
        }

        public RequestRemittanceCommandBuilder toAccountNumber(String toAccountNumber) {
            this.toAccountNumber = toAccountNumber;
            return this;
        }

        public RequestRemittanceCommandBuilder requestType(RemittanceType requestType) {
            this.requestType = requestType;
            return this;
        }

        public RequestRemittanceCommandBuilder amount(@NotNull @Positive Long amount) {
            this.amount = amount;
            return this;
        }

        public RequestRemittanceCommandBuilder description(String description) {
            this.description = description;
            return this;
        }

        public RequestRemittanceCommandBuilder requestStatus(RemittanceStatus requestStatus) {
            this.requestStatus = requestStatus;
            return this;
        }

        public RequestRemittanceCommand build() {
            return new RequestRemittanceCommand(senderId, receiverId, toBankName, toAccountNumber, requestType, amount, description, requestStatus);
        }

        public String toString() {
            return "RequestRemittanceCommand.RequestRemittanceCommandBuilder(senderId=" + this.senderId + ", receiverId=" + this.receiverId + ", toBankName=" + this.toBankName + ", toAccountNumber=" + this.toAccountNumber + ", requestType=" + this.requestType + ", amount=" + this.amount + ", description=" + this.description + ", requestStatus=" + this.requestStatus + ")";
        }
    }
}
