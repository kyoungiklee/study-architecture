package org.opennuri.study.architecture.membership.adapter.in.web;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.common.WebAdapter;
import org.opennuri.study.architecture.membership.appication.port.in.ModifyMembershipCommand;
import org.opennuri.study.architecture.membership.domain.Membership;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(path = "/membership/{membershipId}")
    ResponseEntity<MembershipResponse> modifyMembership(@PathVariable(value = "membershipId") String membershipId, @RequestBody ModifyMembershipRequest request) {
        long longOfMembership;
        try {
            longOfMembership = Long.parseLong(membershipId);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(new MembershipResponse(), null, HttpStatus.BAD_REQUEST);
        }

        ModifyMembershipCommand command = ModifyMembershipCommand.builder()
                .membershipId(longOfMembership)
                .name(request.getName())
                .email(request.getEmail())
                .address(request.getAddress())
                .isValid(request.isValid())
                .isCorp(request.isCorp())
                .build();

        Membership membership = ModifyMembershipUseCase.modifyMembership(command);

        MembershipResponse membershipResponse = new MembershipResponse(
                membership.getMembershipId(),
                membership.getName(),
                membership.getEmail(),
                membership.getAddress(),
                membership.isCorp()
        );
        return new ResponseEntity<>(membershipResponse, null, HttpStatus.OK);
    }
}
