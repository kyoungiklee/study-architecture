package org.opennuri.study.architecture.membership.adapter.in.web;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MembershipResponse {

    private Long membershipId;
    private String name;
    private String email;
    private String address;
    private boolean isCorp;

    @Override
    public String toString() {
        return "MembershipResponse{" +
                "membershipId=" + membershipId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", isCorp=" + isCorp +
                '}';
    }
}
