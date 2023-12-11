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
@Table(name = "member_money")
@AllArgsConstructor
@NoArgsConstructor
public class MemberMoneyJpaEntity extends BaseEntity {
    @Id
    @GeneratedValue
    private Long MemberMoneyId;
    private Long membershipId;
    private Long moneyAmount;

    public MemberMoneyJpaEntity(Long membershipId, Long moneyAmount) {
        this.membershipId = membershipId;
        this.moneyAmount = moneyAmount;
    }

    public void increaseMoney(Long value) {
        this.moneyAmount += value;
    }
}
