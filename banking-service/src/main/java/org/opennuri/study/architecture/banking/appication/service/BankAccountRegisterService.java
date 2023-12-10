package org.opennuri.study.architecture.banking.appication.service;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.banking.adapter.out.external.bank.BankAccount;
import org.opennuri.study.architecture.banking.adapter.out.external.bank.GetBankAccountInfoRequest;
import org.opennuri.study.architecture.banking.appication.port.in.RegisterBankAccountCommand;
import org.opennuri.study.architecture.banking.appication.port.in.RegisterBankAccountUseCase;
import org.opennuri.study.architecture.banking.appication.port.out.RegisterBankAccountPort;
import org.opennuri.study.architecture.banking.appication.port.out.RequestBankAccountInfoPort;
import org.opennuri.study.architecture.banking.domain.RegisteredBankAccount;
import org.opennuri.study.architecture.common.UseCase;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor(access = AccessLevel.MODULE)
public class BankAccountRegisterService implements RegisterBankAccountUseCase {

    @NonNull
    private final RegisterBankAccountPort registerBankAccountPort;

    @NonNull
    private final RequestBankAccountInfoPort requestBankAccountInfoPort;

    @Override
    public RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command) {

        // 1. 회원이 유효한지 membership-service에 확인한다.
        //todo 회원이 유효한지 membership-service에 확인

        // 2. 외부 은행 시스템에 요청하는 계좌에 대한 정보를 획득한다.
        //  Biz Logic -> Port -> Adapter -> External System
        GetBankAccountInfoRequest getBankAccountInfoRequest = GetBankAccountInfoRequest.builder()
                .bankName(command.getBankName())
                .bankAccountNumber(command.getBankAccountNumber())
                .build();

       BankAccount accountInfo = requestBankAccountInfoPort.getBankAccountInfo(getBankAccountInfoRequest);

       boolean accountIsValid = accountInfo.isValid();

       if(accountIsValid) {
           // 등록을 요청한 계좌가 정상이면 은행계좌를 등록한다.
           // 등록 성공한 정보를 리턴한다.
           return registerBankAccountPort.createRegisteredBankAccount(
                       new RegisteredBankAccount.MembershipId(command.getMembershipId())
                       , new RegisteredBankAccount.BankName(command.getBankName())
                       , new RegisteredBankAccount.BankAccountNumber(command.getBankAccountNumber())
                       , new RegisteredBankAccount.ValidLinkedStatus(command.isValidLinkedStatus())
           );
       } else {
           // todo 실패한 경우 적절한 Exception 처리로 변경한다.
           // 실패한 경우 null 을 리턴한다.
           return null;
       }
    }
}
