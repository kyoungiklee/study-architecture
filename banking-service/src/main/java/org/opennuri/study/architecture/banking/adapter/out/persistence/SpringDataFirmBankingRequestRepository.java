package org.opennuri.study.architecture.banking.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataFirmBankingRequestRepository
        extends JpaRepository<FirmBankingRequestedJpaEntity, Long>{
    FirmBankingRequestedJpaEntity findByUuid(String uuid);
}
