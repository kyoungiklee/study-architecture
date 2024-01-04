package org.opennuri.study.architecture.remittance.adapter.out.persistence;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RemittanceRequestRepository extends JpaRepository<RemittanceRequestJpaEntity, Long>, JpaSpecificationExecutor<RemittanceRequestJpaEntity> {
}

