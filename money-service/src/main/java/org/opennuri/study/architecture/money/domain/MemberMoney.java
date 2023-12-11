package org.opennuri.study.architecture.money.domain;


import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMoney {

    private final String membershipId;
    private final Long moneyAmount;
    public record MembershipId(String value) {
        public static MembershipId from(String value) {
            return new MembershipId(value);
        }
    }
    public record MoneyAmount(Long value) {
        public static MoneyAmount from(Long value) {
            return new MoneyAmount(value);
        }
    }
}
