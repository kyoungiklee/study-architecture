package org.opennuri.study.architecture.common.task;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public static RechargingMoneyTask fromJson(String value) {
        // json을 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        RechargingMoneyTask task = null;
        try {
            task = objectMapper.readValue(value, RechargingMoneyTask.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return task;
    }
}
