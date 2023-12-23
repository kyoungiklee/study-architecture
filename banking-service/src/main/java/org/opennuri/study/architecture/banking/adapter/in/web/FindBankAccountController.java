package org.opennuri.study.architecture.banking.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.opennuri.study.architecture.banking.appication.port.in.FindBankAccountUseCase;
import org.opennuri.study.architecture.banking.domain.RegisteredBankAccount;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FindBankAccountController {
    private final FindBankAccountUseCase findBankAccountUseCase;

    @GetMapping("/banking/account/{membershipId}")
    public ResponseEntity<FindBankAccountResponse> findBankAccount(@PathVariable(value = "membershipId") String membershipId) {
        if (membershipId == null || membershipId.isEmpty()) {
            throw new IllegalArgumentException("membershipId is null or empty");
        }

        RegisteredBankAccount bankAccount = findBankAccountUseCase.findBankAccount(Long.valueOf(membershipId))
                .orElseThrow(() -> new IllegalArgumentException("bankAccount is null"));

        FindBankAccountResponse findBankAccountResponse = new FindBankAccountResponse(
                bankAccount.getMembershipId()
                , bankAccount.getBankName()
                , bankAccount.getBankAccountNumber()
                , bankAccount.isValidLinkedStatus());

        return ResponseEntity.ok(findBankAccountResponse);
    }
}
