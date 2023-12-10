package org.opennuri.study.architecture.money.adapter.in.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.money.application.port.in.IncreaseMoneyRequestCommand;
import org.opennuri.study.architecture.money.application.port.in.IncreaseMoneyRequestUseCase;
import org.opennuri.study.architecture.money.domain.ChangingMoneyRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RequestMoneyChangingController {

    private final IncreaseMoneyRequestUseCase increaseMoneyRequestUseCase;

    @PostMapping("/money/increase")
    public ChangingMoneyRequest increaseMoney(@RequestBody IncreaseMoneyChangingRequest request) {
        log.info("increaseMoney: {}", request);

        IncreaseMoneyRequestCommand command = new IncreaseMoneyRequestCommand(
                request.getMembershipId()
                , request.getMoneyAmount()
        );
        return increaseMoneyRequestUseCase.increaseMoneyRequest(command);
    }

}
