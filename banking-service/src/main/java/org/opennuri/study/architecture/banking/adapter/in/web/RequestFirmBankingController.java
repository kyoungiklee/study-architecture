package org.opennuri.study.architecture.banking.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.opennuri.study.architecture.banking.appication.port.in.RequestFirmBankingCommand;
import org.opennuri.study.architecture.banking.appication.port.in.RequestFirmBankingUseCase;
import org.opennuri.study.architecture.banking.domain.FirmBankingRequest;
import org.opennuri.study.architecture.common.WebAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RequiredArgsConstructor
@RestController
public class RequestFirmBankingController {

    private final RequestFirmBankingUseCase requestFirmBankingUseCase;

    @PostMapping(path = "/banking/firmbanking/request")
    FirmBankingRequest requestFirmBanking(@RequestBody RequestFirmBankingRequest request) {

        return requestFirmBankingUseCase.requestFirmBanking(
                RequestFirmBankingCommand.builder()
                        .membershipId(request.getMembershipId())
                        .fromBankName(request.getFromBankName())
                        .fromBankAccountNumber(request.getFromBankAccountNumber())
                        .toBankName(request.getToBankName())
                        .toBankAccountNumber(request.getToBankAccountNumber())
                        .moneyAmount(request.getMoneyAmount())
                        .description(request.getDescription())
                        .build()
        );
    }
}
