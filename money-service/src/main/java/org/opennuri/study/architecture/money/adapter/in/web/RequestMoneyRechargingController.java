package org.opennuri.study.architecture.money.adapter.in.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.money.application.port.in.RechargingMoneyRequestCommand;
import org.opennuri.study.architecture.money.application.port.in.RechargingMoneyRequestUseCase;
import org.opennuri.study.architecture.money.domain.MemberMoney;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RequestMoneyRechargingController {
    private final RechargingMoneyRequestUseCase rechargingMoneyRequestUseCase;

    @PostMapping("/money/recharging")
    public ResponseEntity<MemberMoneyResponse> requestMoneyRecharging(@RequestBody RechargingMoneyRequest rechargingMoneyRequest) {
        log.info("requestMoneyRecharging: {}", rechargingMoneyRequest);

        RechargingMoneyRequestCommand command = RechargingMoneyRequestCommand.builder()
                .membershipId(rechargingMoneyRequest.getMembershipId())
                .moneyAmount(rechargingMoneyRequest.getRechargingAmount())
                .build();

        log.info("command: {}", command);
        MemberMoney memberMoney;
        try {
           memberMoney = rechargingMoneyRequestUseCase.rechargingMoney(command);
        } catch (Exception e) {
            MemberMoneyResponse memberMoneyResponse = MemberMoneyResponse.builder()
                    .membershipId(rechargingMoneyRequest.getMembershipId())
                    .balance(0L)
                    .valid(false)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(memberMoneyResponse, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        MemberMoneyResponse memberMoneyResponse = MemberMoneyResponse.builder()
                .membershipId(memberMoney.getMembershipId())
                .balance(memberMoney.getMoneyAmount())
                .valid(true)
                .message("SUCCESS")
                .build();

        log.info("memberMoneyResponse: {}", memberMoneyResponse);

        return ResponseEntity.ok(memberMoneyResponse);
    }
}
