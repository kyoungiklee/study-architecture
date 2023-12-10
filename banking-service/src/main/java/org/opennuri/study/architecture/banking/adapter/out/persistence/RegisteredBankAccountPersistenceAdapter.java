package org.opennuri.study.architecture.banking.adapter.out.persistence;


import lombok.RequiredArgsConstructor;
import org.opennuri.study.architecture.banking.appication.port.out.RegisterBankAccountPort;
import org.opennuri.study.architecture.banking.domain.RegisteredBankAccount;
import org.opennuri.study.architecture.common.PersistenceAdapter;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisteredBankAccountPersistenceAdapter implements RegisterBankAccountPort {

    private final RegisteredBankAccountMapper bankAccountMapper;

    private final SpringDataRegisteredBankAccountRepository bankingRepository;
    @Override
    public  RegisteredBankAccount createRegisteredBankAccount(
            RegisteredBankAccount.MembershipId membershipId,
            RegisteredBankAccount.BankName bankName,
            RegisteredBankAccount.BankAccountNumber bankAccountNumber,
            RegisteredBankAccount.ValidLinkedStatus validLinkedStatus
            ) {

        RegisteredBankAccountJpaEntity savedEntity = bankingRepository.save(
                new RegisteredBankAccountJpaEntity(
                        membershipId.getMembershipIdValue(),
                        bankName.getBankNameValue(),
                        bankAccountNumber.getBankAccountNumberValue(),
                        validLinkedStatus.isValidLinkedStatusValue()
                )
        );
        return bankAccountMapper.mapToDomainEntity(savedEntity);
    }

}
