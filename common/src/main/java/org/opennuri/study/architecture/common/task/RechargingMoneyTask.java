package org.opennuri.study.architecture.common.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RechargingMoneyTask {

    private String taskId;
    private String taskName;

    private String membershipId;

    private List<SubTask> subTasks;

    private String toBankName;
    private String toBankAccountNumber;

    private Long moneyAmount;

}
