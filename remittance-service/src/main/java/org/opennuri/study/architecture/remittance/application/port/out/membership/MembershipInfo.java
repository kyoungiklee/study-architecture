package org.opennuri.study.architecture.remittance.application.port.out.membership;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.opennuri.study.architecture.common.SelfValidating;

@Builder
@Getter @Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class MembershipInfo extends SelfValidating<MembershipInfo> {
    @NotNull
    private String membershipId; // 멤버십 아이디
    @NotNull
    private boolean isValid; // 멤버십 유효 여부

    private MembershipInfo(@NotNull String membershipId, @NotNull boolean isValid) {
        this.membershipId = membershipId;
        this.isValid = isValid;
        this.validateSelf();
    }

    @Override
    public String toString() {
        return "MembershipInfo{" +
                "membershipId='" + membershipId + '\'' +
                ", isValid=" + isValid +
                '}';
    }
}
