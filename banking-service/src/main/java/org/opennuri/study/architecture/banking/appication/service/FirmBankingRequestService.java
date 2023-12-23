package org.opennuri.study.architecture.banking.appication.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.banking.adapter.out.external.bank.ExternalFirmBankingRequest;
import org.opennuri.study.architecture.banking.adapter.out.external.bank.FirmBankingResult;
import org.opennuri.study.architecture.banking.appication.port.in.RequestFirmBankingCommand;
import org.opennuri.study.architecture.banking.appication.port.in.RequestFirmBankingUseCase;
import org.opennuri.study.architecture.banking.appication.port.out.RequestExternalFirmBankingPort;
import org.opennuri.study.architecture.banking.appication.port.out.RequestFirmBankingPort;
import org.opennuri.study.architecture.banking.domain.FirmBankingRequest;
import org.opennuri.study.architecture.banking.adapter.out.persistence.FirmBankingRequestStatus;
import org.opennuri.study.architecture.common.UseCase;

import java.util.UUID;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class FirmBankingRequestService implements RequestFirmBankingUseCase {
    private final RequestFirmBankingPort requestFirmBankingPort;
    private final RequestExternalFirmBankingPort requestExternalFirmBankingPort;


    @Override
    public FirmBankingResult requestFirmBanking(RequestFirmBankingCommand command) {

        // 1. 요청에 대해 정보를 먼저 저장한다.(요청상태 : 요청중)
        FirmBankingRequest firmBankingRequest = requestFirmBankingPort.createFirmBankingRequest(
                new FirmBankingRequest.MembershipId(command.getMembershipId())
                , new FirmBankingRequest.FromBankName(command.getFromBankName())
                , new FirmBankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber())
                , new FirmBankingRequest.ToBankName(command.getToBankName())
                , new FirmBankingRequest.ToBankAccountNumber(command.getToBankAccountNumber())
                , new FirmBankingRequest.MoneyAmount(command.getMoneyAmount())
                , new FirmBankingRequest.RequestStatus(FirmBankingRequestStatus.REQUESTED)
                , new FirmBankingRequest.RejectReason("")
                , new FirmBankingRequest.Description(command.getDescription())
                , new FirmBankingRequest.Uuid(UUID.randomUUID().toString())
        );

        log.info("firmBankingRequest : {}", firmBankingRequest.toString());
        // 2. 요청에 대해 회원이 정상적인 회원인지 확인한다.
        // 3. 요청에 대해 회원이 충분한 잔액이 있는지 확인한다.
        // 4. 잔액이 부족한 경우 충전을 요청한다.

        // 5. 요청에 대해 외부 은행망 시스템에 요청을 보낸다. (외부 시스템에 대한 포트를 만들어서 사용한다.)
        ExternalFirmBankingRequest externalFirmBankingRequest = ExternalFirmBankingRequest.builder()
                .fromBankName(command.getFromBankName())
                .fromBankAccountNumber(command.getFromBankAccountNumber())
                .toBankName(command.getToBankName())
                .toBankAccountNumber(command.getToBankAccountNumber())
                .moneyAmount(command.getMoneyAmount())
                .membershipId(command.getMembershipId())
                .description(command.getDescription())
                .build();

        // 6. 외부 시스템에 대한 응답을 받는다.
        FirmBankingResult firmBankingResult = requestExternalFirmBankingPort.requestExternalFirmBanking(externalFirmBankingRequest);
        log.info("firmBankingResult : {}", firmBankingResult.toString());

        if(FirmBankingResult.FirmBankingResultCode.SUCCESS.equals(firmBankingResult.getResultCode())) {
            // 7. 응답이 성공이면 요청에 대한 상태를 완료로 변경한다.
           requestFirmBankingPort.updateFirmBankingRequestStatus(firmBankingRequest.getUuid(), FirmBankingRequestStatus.APPROVED);
        } else {
            // 8. 응답이 실패이면 요청에 대한 상태를 실패로 변경한다.
            requestFirmBankingPort.updateFirmBankingRequestStatus(firmBankingRequest.getUuid(), FirmBankingRequestStatus.REJECTED);
        }
        return firmBankingResult;
    }
}
