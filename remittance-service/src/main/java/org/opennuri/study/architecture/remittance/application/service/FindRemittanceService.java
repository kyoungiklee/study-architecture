package org.opennuri.study.architecture.remittance.application.service;

import lombok.RequiredArgsConstructor;
import org.opennuri.study.architecture.common.UseCase;
import org.opennuri.study.architecture.remittance.application.port.in.RemittanceSearchCommand;
import org.opennuri.study.architecture.remittance.application.port.in.FindRemittanceUseCase;
import org.opennuri.study.architecture.remittance.application.port.out.FindRemittancePort;
import org.opennuri.study.architecture.remittance.domain.RemittanceRequest;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class FindRemittanceService implements FindRemittanceUseCase {

    private final FindRemittancePort findRemittancePort;

    /**
     * 송금내역 조회
     * @param command 조회조건
     * @return 송금내역
     */
    @Override
    public List<RemittanceRequest> findRemittanceHistory(RemittanceSearchCommand command) {
        return findRemittancePort.findRemittanceHistory(command);
    }

}
