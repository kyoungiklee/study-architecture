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
    @GetMapping(path = "/membership/findMembership/{membershipId}")
    ResponseEntity<Membership> findMembershipByMemberId(@PathVariable String membershipId ){
        //TODO command 객체 생성
        FindMembershipCommand command = FindMembershipCommand.builder()
                .membershipId(membershipId)
                .build();

        Membership membership = findMembershipUseCase.findMembership(command);
        return ResponseEntity.ok(membership);
    }
}
