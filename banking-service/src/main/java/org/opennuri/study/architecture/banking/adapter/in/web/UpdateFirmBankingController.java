package org.opennuri.study.architecture.banking.adapter.in.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.banking.appication.port.in.UpdateFirmBankingCommand;
import org.opennuri.study.architecture.banking.appication.port.in.UpdateFirmBankingUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UpdateFirmBankingController {

    private final UpdateFirmBankingUseCase updateFirmBankingUseCase;

    @PutMapping("/banking/firmbanking/update-eda")
    public ResponseEntity<UpdateFirmBankingResponse> updateFirmbankingByEvent(@RequestBody UpdateFirmBankingRequest request) {

        log.info("updateFirmbankingByEvent request: {}", request);
        UpdateFirmBankingCommand command = UpdateFirmBankingCommand.builder()
                .aggregateId(request.getAggregateId())
                .status(request.getStatus())
                .build();

        UpdateFirmBankingResponse response;
        try{
            response = updateFirmBankingUseCase.updateFirmbankingByEvent(command);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("updateFirmbankingByEvent error: {}", e.getMessage());
            UpdateFirmBankingResponse errorResponse = UpdateFirmBankingResponse.builder()
                    .aggregateId(request.getAggregateId())
                    .status(request.getStatus())
                    .description(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
