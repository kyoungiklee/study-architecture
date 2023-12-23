package org.opennuri.study.architecture.remittance.adapter.out.persistence;


import org.springframework.stereotype.Repository;

@Repository
public interface RemittanceRequestRepository extends org.springframework.data.jpa.repository.JpaRepository<RemittanceRequestJpaEntity, Long> {
}

