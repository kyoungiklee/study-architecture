package org.opennuri.study.architecture.membership.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyMembershipRequest {
    private String name;
    private String address;
    private String email;
    private boolean isValid;
    private boolean isCorp;
}
