package org.opennuri.study.architecture.remittance.application.port.out;

import org.opennuri.study.architecture.remittance.application.port.in.RemittanceSearchCommand;
import org.opennuri.study.architecture.remittance.domain.RemittanceRequest;

import java.util.List;

public interface FindRemittancePort {

    List<RemittanceRequest> findRemittanceHistory(RemittanceSearchCommand command);
}
