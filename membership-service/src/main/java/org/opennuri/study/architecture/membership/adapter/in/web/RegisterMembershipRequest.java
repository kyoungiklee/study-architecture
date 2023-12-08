package org.opennuri.study.architecture.membership.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterMembershipRequest {
    private String name;
    private String address;
    private String email;
    private boolean isCorp;
}
