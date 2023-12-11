package org.opennuri.study.architecture.money.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.opennuri.study.architecture.common.BaseEntity;

@Entity
@Table(name = "member_money")
@AllArgsConstructor
@NoArgsConstructor
public class MemberMoneyEntity extends BaseEntity {
    @Id
    @GeneratedValue
    private Long MemberMoneyId;
    private Long membershipId;
    private Long moneyAmount;

    public MemberMoneyEntity(Long membershipId, Long moneyAmount) {
        this.membershipId = membershipId;
        this.moneyAmount = moneyAmount;
    }

}
