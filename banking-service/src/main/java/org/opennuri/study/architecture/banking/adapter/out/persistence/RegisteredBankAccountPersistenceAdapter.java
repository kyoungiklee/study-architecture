package org.opennuri.study.architecture.banking.adapter.out.persistence;


import lombok.RequiredArgsConstructor;
import org.opennuri.study.architecture.banking.appication.port.out.FindBankAccountPort;
import org.opennuri.study.architecture.banking.appication.port.out.RegisterBankAccountPort;
import org.opennuri.study.architecture.banking.domain.RegisteredBankAccount;
import org.opennuri.study.architecture.common.PersistenceAdapter;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisteredBankAccountPersistenceAdapter implements RegisterBankAccountPort, FindBankAccountPort {

    private final RegisteredBankAccountMapper bankAccountMapper;

    private final SpringDataRegisteredBankAccountRepository bankingRepository;
    @Override
    public  RegisteredBankAccount createRegisteredBankAccount(
            RegisteredBankAccount.MembershipId membershipId,
            RegisteredBankAccount.BankName bankName,
            RegisteredBankAccount.BankAccountNumber bankAccountNumber,
            RegisteredBankAccount.ValidLinkedStatus validLinkedStatus,
            RegisteredBankAccount.AggregateId aggregateId
            ) {

        RegisteredBankAccountJpaEntity savedEntity = bankingRepository.save(
                new RegisteredBankAccountJpaEntity(
                        membershipId.getMembershipIdValue(),
                        bankName.getBankNameValue(),
                        bankAccountNumber.getBankAccountNumberValue(),
                        validLinkedStatus.isValidLinkedStatusValue(),
                        aggregateId.getAggregateIdValue()
                )
        );
        return bankAccountMapper.mapToDomainEntity(savedEntity);
    }

    @Override
    public RegisteredBankAccount findBankAccount(Long membershipId) {

        RegisteredBankAccountJpaEntity entity = bankingRepository.findByMembershipId(membershipId);
        if(entity == null) {
            throw new IllegalArgumentException("bankAccount is null");
        }
        return bankAccountMapper.mapToDomainEntity(entity);
    }

    @Override
    public RegisteredBankAccount findBankAccount(String bankName, String bankAccountNumber) {
        RegisteredBankAccountJpaEntity byBankNameAndBankAccountNumber
                = bankingRepository.findByBankNameAndBankAccountNumber(bankName, bankAccountNumber);
        if(byBankNameAndBankAccountNumber == null) {
            throw new IllegalArgumentException("bankAccount is null");
        }
        return bankAccountMapper.mapToDomainEntity(byBankNameAndBankAccountNumber);
    }
}
