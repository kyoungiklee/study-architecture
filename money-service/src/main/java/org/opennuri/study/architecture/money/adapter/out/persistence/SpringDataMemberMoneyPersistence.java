package org.opennuri.study.architecture.money.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataMemberMoneyPersistence extends JpaRepository<MemberMoneyJpaEntity, Long> {
    MemberMoneyJpaEntity findByMembershipId(Long value);
}
