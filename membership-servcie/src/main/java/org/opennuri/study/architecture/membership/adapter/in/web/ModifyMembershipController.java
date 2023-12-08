package org.opennuri.study.architecture.membership.adapter.in.web;


import common.WebAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.membership.appication.port.in.ModifyMembershipCommand;
import org.opennuri.study.architecture.membership.appication.port.in.ModifyMembershipUseCase;
import org.opennuri.study.architecture.membership.appication.port.in.RegisterMembershipCommand;
import org.opennuri.study.architecture.membership.appication.port.in.RegisterMembershipUseCase;
import org.opennuri.study.architecture.membership.domain.Membership;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@WebAdapter
@RestController
@RequiredArgsConstructor
public class ModifyMembershipController {

    private final org.opennuri.study.architecture.membership.appication.port.in.ModifyMembershipUseCase ModifyMembershipUseCase;

    @PostMapping(path = "/membership/modify/{membershipId}")
    Membership modifyMembership(@PathVariable("membershipId") String membershipId, @RequestBody ModifyMembershipRequest request) {
        ModifyMembershipCommand command = ModifyMembershipCommand.builder()
                .membershipId(membershipId)
                .name(request.getName())
                .email(request.getEmail())
                .address(request.getAddress())
                .isValid(request.isValid())
                .isCorp(request.isCorp())
                .build();

        return ModifyMembershipUseCase.modifyMembership(command);

    }
}
