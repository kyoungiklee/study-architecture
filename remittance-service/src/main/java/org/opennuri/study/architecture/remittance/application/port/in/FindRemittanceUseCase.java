package org.opennuri.study.architecture.remittance.application.port.in;

import org.opennuri.study.architecture.common.UseCase;
import org.opennuri.study.architecture.remittance.domain.RemittanceRequest;

import java.util.List;

@UseCase
public interface FindRemittanceUseCase {
    List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command);

}
