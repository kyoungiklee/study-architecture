package org.opennuri.study.architecture.remittance.adapter.in;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.common.WebAdapter;
import org.opennuri.study.architecture.remittance.application.port.in.RequestRemittanceCommand;
import org.opennuri.study.architecture.remittance.application.port.in.RequestRemittanceUseCase;
import org.opennuri.study.architecture.remittance.domain.RemittanceRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@WebAdapter
@RequiredArgsConstructor
public class RequestRemittanceController {
     private final RequestRemittanceUseCase requestRemittanceUseCase;
    @PostMapping("/remittance")
    public ResponseEntity<RemittanceResponseVO> requestRemittance(@RequestBody RemittanceRequestVO request) {

        log.info("requestRemittance request: {}", request);

        RequestRemittanceCommand command = RequestRemittanceCommand.builder()
                .senderId(request.getSenderId())
                .receiverId(request.getReceiverId())
                .toBankName(request.getToBankName())
                .toAccountNumber(request.getToAccountNumber())
                .requestType(request.getRequestType())
                .amount(request.getAmount())
                .description(request.getDescription())
                .build();

        log.info("requestRemittance command: {}", command);

        RemittanceRequest remittanceRequest;
        try {
            // RequestRemittanceCommand를 통해 RequestRemittanceUseCase를 호출한다.
            remittanceRequest = requestRemittanceUseCase.requestRemittance(command);
        } catch (Exception e) {
            log.error("requestRemittance error: {}", e.getMessage());
            RemittanceResponseVO remittanceResponseVO = RemittanceResponseVO.builder()
                    .remittanceId(null)
                    .senderId(request.getSenderId())
                    .receiverId(request.getReceiverId())
                    .toBankName(request.getToBankName())
                    .toAccountNumber(request.getToAccountNumber())
                    .requestType(request.getRequestType())
                    .amount(request.getAmount())
                    .description(request.getDescription())
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(remittanceResponseVO, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("requestRemittance remittanceRequest: {}", remittanceRequest);

        RemittanceResponseVO remittanceResponseVO = RemittanceResponseVO.builder()
                .remittanceId(remittanceRequest.getRemittanceRequestId())
                .senderId(remittanceRequest.getSenderId())
                .receiverId(remittanceRequest.getReceiverId())
                .toBankName(remittanceRequest.getToBankName())
                .toAccountNumber(remittanceRequest.getToAccountNumber())
                .requestType(remittanceRequest.getRequestType())
                .amount(remittanceRequest.getAmount())
                .description(remittanceRequest.getDescription())
                .message("SUCCESS")
                .build();
        return new ResponseEntity<>(remittanceResponseVO, null, HttpStatus.CREATED);

    }
}
