package org.opennuri.study.architecture.money.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataChangingMoneyPersistence extends JpaRepository<MoneyChangingRequestJpaEntity, Long> {
    Optional<MoneyChangingRequestJpaEntity> findByUuid(String uuid);

}
