package org.opennuri.study.architecture.money.adapter.in.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.common.exception.BusinessException;
import org.opennuri.study.architecture.money.application.port.in.CreateMoneyRequestCommand;
import org.opennuri.study.architecture.money.application.port.in.CreateMemberMoneyUseCase;
import org.opennuri.study.architecture.money.domain.MemberMoney;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RequestMoneyCreateController {
    private final CreateMemberMoneyUseCase createMemberMoneyUseCase;

    @PostMapping("/money/create")
    public ResponseEntity<MemberMoney> createMoney(@RequestBody CreateMoneyRequest request) {
        log.info("createMemberMoney: {}", request);

        MemberMoney memberMoney = null;
        try {
            memberMoney = createMemberMoneyUseCase.createMemberMoney(
                    CreateMoneyRequestCommand.builder()
                            .membershipId(request.getMembershipId())
                            .moneyAmount(request.getMoneyAmount())
                            .build());

        } catch (BusinessException e) {
            return ResponseEntity.badRequest()
                    .build();
        }

        return ResponseEntity.ok(memberMoney);
    }
}
