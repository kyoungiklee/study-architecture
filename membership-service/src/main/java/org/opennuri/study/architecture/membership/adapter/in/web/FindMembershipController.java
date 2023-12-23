package org.opennuri.study.architecture.membership.adapter.in.web;



import lombok.RequiredArgsConstructor;
import org.opennuri.study.architecture.common.WebAdapter;
import org.opennuri.study.architecture.membership.appication.port.in.FindMembershipCommand;
import org.opennuri.study.architecture.membership.appication.port.in.FindMembershipUseCase;
import org.opennuri.study.architecture.membership.domain.Membership;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@WebAdapter
@RequiredArgsConstructor
public class FindMembershipController {

    private final FindMembershipUseCase findMembershipUseCase;
    @GetMapping(path = "/membership/{membershipId}")
    public ResponseEntity<MembershipResponse> findMembershipByMemberId(@PathVariable(value = "membershipId") String membershipId ){
        long longOfMembership;
        try {
            longOfMembership = Long.parseLong(membershipId);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }

        FindMembershipCommand command = FindMembershipCommand.builder()
                .membershipId(longOfMembership)
                .build();

        Membership membership = findMembershipUseCase.findMembership(command);
        MembershipResponse membershipResponse = new MembershipResponse(
                membership.getMembershipId(),
                membership.getName(),
                membership.getEmail(),
                membership.getAddress(),
                membership.isCorp()
        );
        return ResponseEntity.ok(membershipResponse);
    }
}
