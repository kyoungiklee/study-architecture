package org.opennuri.study.architecture.money.adapter.in.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.money.application.port.in.IncreaseMoneyRequestCommand;
import org.opennuri.study.architecture.money.application.port.in.IncreaseMoneyRequestUseCase;
import org.opennuri.study.architecture.money.domain.MoneyChangingRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RequestMoneyChangingController {

    private final IncreaseMoneyRequestUseCase increaseMoneyRequestUseCase;

    @PostMapping("/money/increase")
    public MoneyChangingResultDetail increaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request) {
        log.info("increaseMoney: {}", request);

        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .membershipId(request.getMembershipId())
                .moneyAmount(request.getMoneyAmount())
                .build();

        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequest(command);

        // MoneyChangingRequest -> MoneyChangingResultDetail
        return new MoneyChangingResultDetail(
                moneyChangingRequest.getMembershipId().toString(),
                moneyChangingRequest.getMoneyAmount(),
                moneyChangingRequest.getRequestStatus()
        );
    }

    @PostMapping("/money/increase-async")
    public MoneyChangingResultDetail increaseMoneyChangingRequestAsync(@RequestBody IncreaseMoneyChangingRequest request) {
        log.info("increaseMoney: {}", request);

        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .membershipId(request.getMembershipId())
                .moneyAmount(request.getMoneyAmount())
                .build();

        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequestAsync(command);

        // MoneyChangingRequest -> MoneyChangingResultDetail
        return new MoneyChangingResultDetail(
                moneyChangingRequest.getMembershipId().toString(),
                moneyChangingRequest.getMoneyAmount(),
                moneyChangingRequest.getRequestStatus()
        );
    }

}
