package org.opennuri.study.architecture.money.application.port.out.kafka;

import org.opennuri.study.architecture.common.task.RechargingMoneyTask;

public interface SendRechargingMoneyTaskPort {
    void sendRechargingMoneyTask(RechargingMoneyTask task);
}
