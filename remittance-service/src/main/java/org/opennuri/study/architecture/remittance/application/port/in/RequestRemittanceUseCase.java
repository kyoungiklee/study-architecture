package org.opennuri.study.architecture.remittance.application.port.in;

import org.opennuri.study.architecture.remittance.domain.RemittanceRequest;


public interface RequestRemittanceUseCase {
    RemittanceRequest requestRemittance(RequestRemittanceCommand command);
}
