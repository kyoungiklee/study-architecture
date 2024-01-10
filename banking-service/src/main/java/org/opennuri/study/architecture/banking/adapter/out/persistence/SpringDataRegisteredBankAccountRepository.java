package org.opennuri.study.architecture.banking.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataRegisteredBankAccountRepository
        extends JpaRepository<RegisteredBankAccountJpaEntity, Long> {
    RegisteredBankAccountJpaEntity findByMembershipId(Long membershipId);

    RegisteredBankAccountJpaEntity findByBankNameAndBankAccountNumber(String bankName, String bankAccountNumber);
}
