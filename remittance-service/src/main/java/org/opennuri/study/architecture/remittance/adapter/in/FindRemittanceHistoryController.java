package org.opennuri.study.architecture.remittance.adapter.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.remittance.application.port.in.RemittanceSearchCommand;
import org.opennuri.study.architecture.remittance.application.port.in.FindRemittanceUseCase;
import org.opennuri.study.architecture.remittance.domain.RemittanceRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FindRemittanceHistoryController {
    private final FindRemittanceUseCase findRemittanceUseCase;
    private final ObjectMapper objectMapper;

    @PostMapping("/remittance/history")
    public ResponseEntity<String> findRemittanceHistory(@RequestBody RemittanceRequestVO remittanceRequestVO) {
        log.info("findRemittanceHistory : {}", remittanceRequestVO);

        RemittanceSearchCommand command = RemittanceSearchCommand.builder()
                .senderId(remittanceRequestVO.getSenderId())
                .receiverId(remittanceRequestVO.getReceiverId())
                .toBankName(remittanceRequestVO.getToBankName())
                .toAccountNumber(remittanceRequestVO.getToAccountNumber())
                .requestType(remittanceRequestVO.getRequestType())
                .amount(remittanceRequestVO.getAmount())
                .description(remittanceRequestVO.getDescription())
                .build();


        List<RemittanceRequest> remittanceHistory = findRemittanceUseCase.findRemittanceHistory(command);

        List<RemittanceResponseVO> remittanceResponseVOList = remittanceHistory.stream().map(remittanceRequest -> {
                    log.info("remittanceRequest : {}", remittanceRequest);
                    return objectMapper.convertValue(remittanceRequest, RemittanceResponseVO.class);
                })
                .toList();
        String result;
        try {
            result = objectMapper.writeValueAsString(remittanceResponseVOList);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException : {}", e.getMessage());
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(result, null, HttpStatus.OK);
    }
}
