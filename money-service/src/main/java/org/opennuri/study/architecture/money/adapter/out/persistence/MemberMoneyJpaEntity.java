package org.opennuri.study.architecture.money.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.opennuri.study.architecture.common.BaseEntity;

@Entity
@Getter @Setter
@Table(name = "MEMBER_MONEY")
@AllArgsConstructor
@NoArgsConstructor
public class MemberMoneyJpaEntity extends BaseEntity {
    @Id
    @GeneratedValue
    private Long MemberMoneyId;
    private Long membershipId;
    private Long balance;
    private String aggregateId;

    public MemberMoneyJpaEntity(Long membershipId, Long balance, String aggregateId) {
        this.membershipId = membershipId;
        this.balance = balance;
        this.aggregateId = aggregateId;
    }

    public void increaseMoney(Long value) {
        this.balance += value;
    }

    public void decreaseMoney(Long value) {
        this.balance -= value;
    }
}
