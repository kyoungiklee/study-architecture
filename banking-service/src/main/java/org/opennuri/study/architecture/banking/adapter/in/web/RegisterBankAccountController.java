package org.opennuri.study.architecture.banking.adapter.in.web;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.banking.appication.port.in.RegisterBankAccountCommand;
import org.opennuri.study.architecture.banking.appication.port.in.RegisterBankAccountUseCase;
import org.opennuri.study.architecture.banking.domain.RegisteredBankAccount;
import org.opennuri.study.architecture.common.WebAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterBankAccountController {

    private final RegisterBankAccountUseCase registerBankAccountUseCase;

    @PostMapping(path = "/banking/account/register")
    RegisteredBankAccount registerBankAccount(@RequestBody RegisterBankAccountRequest request) {
        // request~~~
        // request --> command
        // usecase ~~(request x command o)
        RegisterBankAccountCommand command = RegisterBankAccountCommand.builder()
                .membershipId(request.getMembershipId())
                .bankName(request.getBankName())
                .bankAccountNumber(request.getBankAccountNumber())
                .validLinkedStatus(request.isValidLinkedStatus())
                .build();

        return registerBankAccountUseCase.registerBankAccount(command);
    }
}
