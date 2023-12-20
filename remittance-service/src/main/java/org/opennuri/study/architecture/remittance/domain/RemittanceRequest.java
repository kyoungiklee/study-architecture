package org.opennuri.study.architecture.remittance.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter @Setter
public class RemittanceRequest {
    //송금요청에 대한 정보를 담는 클래스
    private final String remittanceRequestId;
    private final String senderId;
    private final String receiverId;
    private final String toBankName;
    private final String toAccountNumber;
    private final RemittanceType remittanceType;
    private final Long amount;
    private final String description;
    private final RemittanceStatus status;



    public record RemittanceRequestId(String value) {
        public RemittanceRequestId {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException("송금요청 ID는 필수값입니다.");
            }
        }
    }

    public record SenderId(String value) {
        public SenderId {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException("송금요청자 ID는 필수값입니다.");
            }
        }
    }

    public record ReceiverId(String value) {
        public ReceiverId {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException("송금수취인 ID는 필수값입니다.");
            }
        }
    }

    public record ToBankName(String value) {
        public ToBankName {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException("송금수취은행은 필수값입니다.");
            }
        }
    }

    public record ToAccountNumber(String value) {
        public ToAccountNumber {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException("송금수취계좌번호는 필수값입니다.");
            }
        }
    }



    @AllArgsConstructor
    @Getter
    private enum RemittanceType {
        //송금 타입 (내부고객 외부은행)
        INTERNAL("내부고객"),
        EXTERNAL("은행");
        private final String description;
    }

    @AllArgsConstructor
    @Getter
    private enum RemittanceStatus {
        //송금 상태 (송금요청, 송금완료, 송금실패)
        REQUESTED("송금요청"),
        COMPLETED("송금완료"),
        FAILED("송금실패");
        private final String description;
    }
}
