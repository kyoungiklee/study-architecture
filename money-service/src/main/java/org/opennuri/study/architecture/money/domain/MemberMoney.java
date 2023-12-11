package org.opennuri.study.architecture.money.domain;


import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMoney {

    private final Long memberMoneyId;
    private final Long membershipId;
    private final Long moneyAmount;

    public static MemberMoney from(MemberMoneyId memberMoneyId, MembershipId membershipId, MoneyAmount moneyAmount) {
        return new MemberMoney(
                memberMoneyId.value()
                , membershipId.value()
                , moneyAmount.value());
    }

    public record MemberMoneyId(Long value) {
        public static MemberMoneyId from(Long value) {
            return new MemberMoneyId(value);
        }
    }
    public record MembershipId(Long value) {
        public static MembershipId from(Long value) {
            return new MembershipId(value);
        }
    }

    public record MoneyAmount(Long value) {
        public static MoneyAmount from(Long value) {
            return new MoneyAmount(value);
        }
    }
}
