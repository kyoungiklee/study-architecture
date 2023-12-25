package org.opennuri.study.architecture.money.adapter.in.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.money.application.port.in.DecreaseMoneyRequestCommand;
import org.opennuri.study.architecture.money.application.port.in.DecreaseMoneyRequestUseCase;
import org.opennuri.study.architecture.money.application.port.in.IncreaseMoneyRequestCommand;
import org.opennuri.study.architecture.money.application.port.in.IncreaseMoneyRequestUseCase;
import org.opennuri.study.architecture.money.domain.ChangingMoneyRequestStatus;
import org.opennuri.study.architecture.money.domain.MemberMoney;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RequestMoneyChangingController {

    private final IncreaseMoneyRequestUseCase increaseMoneyRequestUseCase;
    private final DecreaseMoneyRequestUseCase decreaseMoneyRequestUseCase;

    @PostMapping("/money/increase")
    public ResponseEntity<ChangingMoneyResponse> increaseMoneyChangingRequest(@RequestBody ChangingMoneyRequest request) {
        log.info("increaseMoney: {}", request);
        try {
            ChangingMoneyResponse moneyChangingResultDetail = increaseMoney(request);
            return ResponseEntity.ok(moneyChangingResultDetail);
        } catch (Exception e) {
            log.error("increaseMoney error", e);

            ChangingMoneyResponse changingMoneyResponse = new ChangingMoneyResponse(
                    request.getMembershipId(),
                    request.getMoneyAmount(),
                    ChangingMoneyRequestStatus.FAILED
            );
            return new ResponseEntity<>(changingMoneyResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ChangingMoneyResponse increaseMoney(ChangingMoneyRequest request) {
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .membershipId(request.getMembershipId())
                .moneyAmount(request.getMoneyAmount())
                .build();

        MemberMoney memberMoney = increaseMoneyRequestUseCase.increaseMoneyRequest(command);

        return new ChangingMoneyResponse(
                memberMoney.getMembershipId().toString(),
                memberMoney.getMoneyAmount(),
                ChangingMoneyRequestStatus.SUCCESS
        );

    }

    @PostMapping("/money/decrease")
    public ResponseEntity<ChangingMoneyResponse> decreaseMoneyChangingRequest(@RequestBody ChangingMoneyRequest request) {
        log.info("decreaseMoney: {}", request);

        try {
            ChangingMoneyResponse moneyChangingResultDetail = decreaseMoney(request);
            return ResponseEntity.ok(moneyChangingResultDetail);
        } catch (Exception e) {
            log.error("decreaseMoney error", e);
            ChangingMoneyResponse changingMoneyResponse = new ChangingMoneyResponse(
                    request.getMembershipId(),
                    request.getMoneyAmount(),
                    ChangingMoneyRequestStatus.FAILED
            );
            return new ResponseEntity<>(changingMoneyResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    private ChangingMoneyResponse decreaseMoney(ChangingMoneyRequest request) {
        long longOfMembershipId;
        try {
            longOfMembershipId = Long.parseLong(request.getMembershipId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("membershipId must be number");
        }

        DecreaseMoneyRequestCommand command = DecreaseMoneyRequestCommand.builder()
                .membershipId(longOfMembershipId)
                .moneyAmount(request.getMoneyAmount())
                .build();

        MemberMoney memberMoney = decreaseMoneyRequestUseCase.decreaseMoneyRequest(command);

        return new ChangingMoneyResponse(
                memberMoney.getMembershipId().toString(),
                memberMoney.getMoneyAmount(),
                ChangingMoneyRequestStatus.SUCCESS
        );
    }

}
