package org.opennuri.study.architecture.common;

import lombok.Value;

import java.time.LocalDateTime;

public class BaseDomainModel {
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;

    @Value
    public static class CreatedDateTime {
        public CreatedDateTime(LocalDateTime createdDateTime) {
            this.createdDateTime = createdDateTime;
        }

        LocalDateTime createdDateTime;
    }

    @Value
    public static class ModifiedDateTime {
        public ModifiedDateTime(LocalDateTime modifiedDateTime) {
            this.modifiedDateTime = modifiedDateTime;
        }

        LocalDateTime modifiedDateTime;
    }

}
