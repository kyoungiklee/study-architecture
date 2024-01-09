package org.opennuri.study.architecture.banking.appication.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.opennuri.study.architecture.banking.adapter.axon.command.CreateFirmbankingRequestCommand;
import org.opennuri.study.architecture.banking.adapter.axon.command.UpdateFirmBankingRequestCommand;
import org.opennuri.study.architecture.banking.adapter.in.web.UpdateFirmBankingResponse;
import org.opennuri.study.architecture.banking.adapter.out.external.bank.ExternalFirmBankingRequest;
import org.opennuri.study.architecture.banking.adapter.out.external.bank.FirmBankingResult;
import org.opennuri.study.architecture.banking.adapter.out.persistence.FirmBankingRequestStatus;
import org.opennuri.study.architecture.banking.appication.port.in.RequestFirmBankingCommand;
import org.opennuri.study.architecture.banking.appication.port.in.RequestFirmBankingUseCase;
import org.opennuri.study.architecture.banking.appication.port.in.UpdateFirmBankingCommand;
import org.opennuri.study.architecture.banking.appication.port.in.UpdateFirmBankingUseCase;
import org.opennuri.study.architecture.banking.appication.port.out.RequestExternalFirmBankingPort;
import org.opennuri.study.architecture.banking.appication.port.out.RequestFirmBankingPort;
import org.opennuri.study.architecture.banking.domain.FirmBankingRequest;
import org.opennuri.study.architecture.common.UseCase;
import org.opennuri.study.architecture.common.exception.BusinessCreateException;
import org.opennuri.study.architecture.common.exception.BusinessException;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class FirmBankingRequestService implements RequestFirmBankingUseCase, UpdateFirmBankingUseCase {
    private final RequestFirmBankingPort requestFirmBankingPort;
    private final RequestExternalFirmBankingPort requestExternalFirmBankingPort;

    private final CommandGateway commandGateway;


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
                , new FirmBankingRequest.AggregateId("")
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

        if (FirmBankingResult.FirmBankingResultCode.SUCCESS.equals(firmBankingResult.getResultCode())) {
            // 7. 응답이 성공이면 요청에 대한 상태를 완료로 변경한다.
            requestFirmBankingPort.updateFirmBankingRequestStatus(firmBankingRequest.getUuid(), FirmBankingRequestStatus.APPROVED);
        } else {
            // 8. 응답이 실패이면 요청에 대한 상태를 실패로 변경한다.
            requestFirmBankingPort.updateFirmBankingRequestStatus(firmBankingRequest.getUuid(), FirmBankingRequestStatus.REJECTED);
        }
        return firmBankingResult;
    }

    @Override
    public FirmBankingResult requestFirmBankingByEvent(RequestFirmBankingCommand command) {
        log.info("FirmBankingRequestService requestFirmBankingByEvent command : {}", command);

        CreateFirmbankingRequestCommand createFirmbankingRequestCommand = CreateFirmbankingRequestCommand.builder()
                .toBankName(command.getToBankName())
                .toBankAccountNumber(command.getToBankAccountNumber())
                .fromBankName(command.getFromBankName())
                .fromBankAccountNumber(command.getFromBankAccountNumber())
                .amount(command.getMoneyAmount())
                .build();

        CompletableFuture<FirmBankingResult> firmBankingResultCompletableFuture = commandGateway.send(createFirmbankingRequestCommand)
                .whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        log.error("FirmBankingRequestService requestFirmBankingByEvent error : {}", throwable.getMessage());
                        throw new BusinessCreateException(throwable.getMessage());
                    }
                }).thenApply(result -> {
                    log.info("FirmBankingRequestService requestFirmBankingByEvent result : {}", result.toString());
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
                            , new FirmBankingRequest.AggregateId(result.toString())
                    );

                    // 2. 요청에 대해 외부 은행망 시스템에 요청을 보낸다. (외부 시스템에 대한 포트를 만들어서 사용한다.)
                    ExternalFirmBankingRequest externalFirmBankingRequest = ExternalFirmBankingRequest.builder()
                            .fromBankName(command.getFromBankName())
                            .fromBankAccountNumber(command.getFromBankAccountNumber())
                            .toBankName(command.getToBankName())
                            .toBankAccountNumber(command.getToBankAccountNumber())
                            .moneyAmount(command.getMoneyAmount())
                            .membershipId(command.getMembershipId())
                            .description(command.getDescription())
                            .build();

                    // 3. 외부 시스템에 대한 응답을 받는다.
                    FirmBankingResult firmBankingResult = requestExternalFirmBankingPort.requestExternalFirmBanking(externalFirmBankingRequest);

                    if (FirmBankingResult.FirmBankingResultCode.SUCCESS.equals(firmBankingResult.getResultCode())) {
                        // 7. 응답이 성공이면 요청에 대한 상태를 완료로 변경한다.
                        requestFirmBankingPort.updateFirmBankingRequestStatus(firmBankingRequest.getUuid(), FirmBankingRequestStatus.APPROVED);
                    } else {
                        // 8. 응답이 실패이면 요청에 대한 상태를 실패로 변경한다.
                        requestFirmBankingPort.updateFirmBankingRequestStatus(firmBankingRequest.getUuid(), FirmBankingRequestStatus.REJECTED);
                    }
                    firmBankingResult.setAggregateId(result.toString());
                    return firmBankingResult;
                });
        try {
            return firmBankingResultCompletableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new BusinessException(e.getMessage());
        }
    }


    @Override
    public UpdateFirmBankingResponse updateFirmbankingByEvent(UpdateFirmBankingCommand command) {

        UpdateFirmBankingRequestCommand updateFirmBankingRequestCommand = UpdateFirmBankingRequestCommand.builder()
                .aggregateId(command.getAggregateId())
                .status(command.getStatus())
                .build();
        CompletableFuture<UpdateFirmBankingResponse> updateFirmBankingResponseCompletableFuture =
                commandGateway.send(updateFirmBankingRequestCommand).whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        log.error("FirmBankingRequestService updateFirmbankingByEvent error : {}", throwable.getMessage());
                        throw new BusinessCreateException(throwable.getMessage());
                    }
                }).thenApply(result -> {
                    log.info("FirmBankingRequestService updateFirmbankingByEvent result : {}", result.toString());
                    FirmBankingRequest firmBankingRequest =
                            requestFirmBankingPort.updateFirmBankingRequestStatusByEvent(command.getAggregateId()
                                    , FirmBankingRequestStatus.APPROVED);

                    return UpdateFirmBankingResponse.builder()
                            .aggregateId(firmBankingRequest.getAggregateId())
                            .status(firmBankingRequest.getRequestStatus())
                            .description(FirmBankingRequestStatus.APPROVED.getStatus())
                            .build();
                });

        return updateFirmBankingResponseCompletableFuture.join();
    }
}
