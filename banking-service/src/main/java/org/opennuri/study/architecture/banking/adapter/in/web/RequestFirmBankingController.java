package org.opennuri.study.architecture.banking.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.opennuri.study.architecture.banking.adapter.out.external.bank.FirmBankingResult;
import org.opennuri.study.architecture.banking.appication.port.in.RequestFirmBankingCommand;
import org.opennuri.study.architecture.banking.appication.port.in.RequestFirmBankingUseCase;
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
    public FirmBankingResult requestFirmBanking(@RequestBody RequestFirmBankingRequest request) {

        // request 객체를 들어도는 데이터 타입은 String이다.
        // command 객체로 생성시 service에서 처리하는 데이터 형으로 변환한다.
        long longOfMembershipId;
        try {
            longOfMembershipId = Long.parseLong(request.getMembershipId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("membershipId is not number");
        }

        return requestFirmBankingUseCase.requestFirmBanking(
                RequestFirmBankingCommand.builder()
                        .membershipId(longOfMembershipId)
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
