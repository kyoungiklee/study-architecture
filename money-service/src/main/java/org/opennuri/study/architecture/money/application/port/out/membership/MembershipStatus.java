package org.opennuri.study.architecture.money.application.port.out.membership;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Data
public class MembershipStatus {
    String membershipId;
    MembershipStatusType membershipStatusType;

    //회원의 상태 (NORMAL: 정상  SUSPENDED: 정지 WITHDRAWAL: 탈퇴)
    @AllArgsConstructor
    @Getter
    public enum MembershipStatusType {
        NORMAL("정상")
        , SUSPENDED("정지")
        , WITHDRAWAL("탈퇴")
        ;
        private final String description;
    }
}
