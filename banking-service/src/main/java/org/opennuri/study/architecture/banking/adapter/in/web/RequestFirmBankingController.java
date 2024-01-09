package org.opennuri.study.architecture.banking.adapter.in.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.banking.adapter.out.external.bank.FirmBankingResult;
import org.opennuri.study.architecture.banking.appication.port.in.RequestFirmBankingCommand;
import org.opennuri.study.architecture.banking.appication.port.in.RequestFirmBankingUseCase;
import org.opennuri.study.architecture.common.WebAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@Slf4j
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

    @PutMapping(path = "/banking/firmbanking/request-eda")
    public ResponseEntity<FirmBankingResult> requestFirmBankingByEvent(@RequestBody RequestFirmBankingRequest request) {

        // request 객체를 들어도는 데이터 타입은 String이다.
        // command 객체로 생성시 service에서 처리하는 데이터 형으로 변환한다.
        long longOfMembershipId;
        try {
            longOfMembershipId = Long.parseLong(request.getMembershipId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("membershipId is not number");
        }

        FirmBankingResult firmBankingResult;
        try {
            firmBankingResult = requestFirmBankingUseCase.requestFirmBankingByEvent(
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
        } catch (ExecutionException | InterruptedException e) {
            log.error("requestFirmBankingByEvent error: {}", e.getMessage());
            FirmBankingResult errorResult = FirmBankingResult.builder()
                    .membershipId(longOfMembershipId)
                    .moneyAmount(request.getMoneyAmount())
                    .resultCode(FirmBankingResult.FirmBankingResultCode.FAIL)
                    .resultMessage(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResult, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(firmBankingResult);
    }
}
