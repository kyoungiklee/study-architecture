package org.opennuri.study.architecture.banking.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataFirmBankingRequestRepository
        extends JpaRepository<FirmBankingRequestedJpaEntity, Long>{
    FirmBankingRequestedJpaEntity findByUuid(String uuid);


    @Query("select f from FirmBankingRequestedJpaEntity f where f.aggregateId = :aggregateId")
    FirmBankingRequestedJpaEntity findByAggregateId(@Param("aggregateId")String aggregateId);
}
