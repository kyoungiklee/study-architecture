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

    public record MemberMoneyId(Long memberMoneyId) {}
    public record MembershipId(Long membershipId) {}
    public record MoneyAmount(Long moneyAmount) {}
}
