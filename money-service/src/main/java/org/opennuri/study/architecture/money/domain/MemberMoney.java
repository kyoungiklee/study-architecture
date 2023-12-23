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
                memberMoneyId.memberMoneyId()
                , membershipId.membershipId()
                , moneyAmount.moneyAmount());
    }

    public record MemberMoneyId(Long memberMoneyId) {
        public static MemberMoneyId from(Long memberMoneyId) {
            return new MemberMoneyId(memberMoneyId);
        }
    }
    public record MembershipId(Long membershipId) {
        public static MembershipId from(Long membershipId) {
            return new MembershipId(membershipId);
        }
    }

    public record MoneyAmount(Long moneyAmount) {
        public static MoneyAmount from(Long moneyAmount) {
            return new MoneyAmount(moneyAmount);
        }
    }
}
