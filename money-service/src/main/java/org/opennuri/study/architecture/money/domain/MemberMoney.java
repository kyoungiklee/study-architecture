package org.opennuri.study.architecture.money.domain;


import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMoney {

    private final Long memberMoneyId;
    private final Long membershipId;
    private final Long balance;
    private final String aggregateId;


    public static MemberMoney from(MemberMoneyId memberMoneyId, MembershipId membershipId, Balance balance, AggregateId aggregateId) {
        return new MemberMoney(
                memberMoneyId.memberMoneyId()
                , membershipId.membershipId()
                , balance.balance()
                , aggregateId.aggregateId());
    }

    public record MemberMoneyId(Long memberMoneyId) {}
    public record MembershipId(Long membershipId) {}
    public record Balance(Long balance) {}
    public record AggregateId(String aggregateId) {}

    @Override
    public String toString() {
        return "MemberMoney{" +
                "memberMoneyId=" + memberMoneyId +
                ", membershipId=" + membershipId +
                ", balance=" + balance +
                ", aggregateId='" + aggregateId + '\'' +
                '}';
    }
}
