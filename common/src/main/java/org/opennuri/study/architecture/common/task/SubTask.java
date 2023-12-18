package org.opennuri.study.architecture.common.task;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SubTask {

    private String membershipId;
    private String subTaskName;
    private SubTaskType subTaskType;
    private SubTaskStatus subTaskStatus;

    @AllArgsConstructor
    @Getter
    public enum SubTaskType {
        MEMBERSHIP("membership"),
        BANKING("banking");

        private final String description;
    }

    @AllArgsConstructor
    @Getter
    public enum SubTaskStatus {
        STARTED("시작"),
        COMPLETED("완료"),
        FAILED("실패");

        private final String description;
    }
}
