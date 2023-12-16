package org.opennuri.study.architecture.money.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MoneyChangingRequest {
    private final Long changingMoneyRequestId; // 식별자
    private final Long membershipId; // 회원 식별자
    private final ChangingMoneyRequestType requestType; // 충전/사용
    private final Long moneyAmount; // 충전 또는 사용 금액
    private final ChangingMoneyRequestStatus requestStatus; // 충전 또는 사용 요청 상태
    private final LocalDateTime requestDataTime; // 충전 또는 사용 요청 시간
    private final String uuid; // 충전 또는 사용 요청 UUID

    public static MoneyChangingRequest getInstance(
            MoneyChangingRequest.ChangingMoneyRequestId changingMoneyRequestId,
            MoneyChangingRequest.MembershipId membershipId,
            MoneyChangingRequest.RequestType requestType,
            MoneyChangingRequest.MoneyAmount moneyAmount,
            MoneyChangingRequest.RequestStatus requestStatus,
            MoneyChangingRequest.RequestDateTime requestDateTime,
            MoneyChangingRequest.UUID uuid
    ) {
        return new MoneyChangingRequest(
                changingMoneyRequestId.changingMoneyRequestId(),
                membershipId.membershipId(),
                requestType.requestType(),
                moneyAmount.moneyAmount(),
                requestStatus.requestStatus(),
                requestDateTime.requestDateTime(),
                uuid.uuid()
        );
    }
    public record ChangingMoneyRequestId(Long changingMoneyRequestId) {}
    public record MembershipId(Long membershipId) {}
    public record RequestType(ChangingMoneyRequestType requestType) {}
    public record MoneyAmount(Long moneyAmount) {}
    public record RequestStatus(ChangingMoneyRequestStatus requestStatus) {}
    public record RequestDateTime(LocalDateTime requestDateTime) {}
    public record UUID(String uuid) {}
}
