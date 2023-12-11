package org.opennuri.study.architecture.money.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.opennuri.study.architecture.common.BaseEntity;
import org.opennuri.study.architecture.money.domain.ChangingMoneyRequestStatus;
import org.opennuri.study.architecture.money.domain.ChangingMoneyRequestType;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MoneyChangingRequestJpaEntity  extends BaseEntity {
    @Id
    @GeneratedValue
    private Long changingMoneyRequestId; // 식별자
    private String membershipId; // 회원 식별자
    @Enumerated(value = EnumType.STRING)
    private ChangingMoneyRequestType requestType; // DEPOSIT("충전"), // 충전, WITHDRAW("사용"); // 사용
    private Long moneyAmount; // 충전 또는 사용 금액
    @Enumerated(value = EnumType.STRING)
    private ChangingMoneyRequestStatus requestStatus; // REQUESTED("요청"), // 요청됨, SUCCESS("성공"), // 성공, FAILED("실패"); // 실패
    private LocalDateTime requestDataTime; // 충전 또는 사용 요청 시간
    private String uuid; // 충전 또는 사용 요청 UUID

    public MoneyChangingRequestJpaEntity(
            String membershipId
            , ChangingMoneyRequestType requestType
            , Long moneyAmount
            , ChangingMoneyRequestStatus requestStatus
            , LocalDateTime requestDataTime
            , String uuid) {
        this.membershipId = membershipId;
        this.requestType = requestType;
        this.moneyAmount = moneyAmount;
        this.requestStatus = requestStatus;
        this.requestDataTime = requestDataTime;
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "MoneyChangingRequestJpaEntity{" +
                "changingMoneyRequestId=" + changingMoneyRequestId +
                ", membershipId='" + membershipId + '\'' +
                ", requestType=" + requestType +
                ", moneyAmount=" + moneyAmount +
                ", requestStatus=" + requestStatus +
                ", requestDataTime=" + requestDataTime +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
