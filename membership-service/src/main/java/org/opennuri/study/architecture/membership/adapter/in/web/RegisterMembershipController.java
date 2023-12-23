package org.opennuri.study.architecture.membership.adapter.in.web;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.common.WebAdapter;
import org.opennuri.study.architecture.membership.appication.port.in.RegisterMembershipCommand;
import org.opennuri.study.architecture.membership.appication.port.in.RegisterMembershipUseCase;
import org.opennuri.study.architecture.membership.domain.Membership;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterMembershipController {

    private final RegisterMembershipUseCase registerMembershipUseCase;

    @PostMapping(path = "/membership/register")
    public ResponseEntity<MembershipResponse> registerMembership(@RequestBody RegisterMembershipRequest request) {

        log.info("registerMembership request: {}", request);
        // request~~~
        // request --> command
        // usecase ~~(request x command o)
        RegisterMembershipCommand command = RegisterMembershipCommand.builder()
                .name(request.getName())
                .email(request.getEmail())
                .address(request.getAddress())
                .isValid(true)
                .isCorp(request.isCorp())
                .build();

        log.info("registerMembership command: {}", command);

        Membership membership = registerMembershipUseCase.resisterMembership(command);
        MembershipResponse membershipResponse = new MembershipResponse(
                membership.getMembershipId(),
                membership.getName(),
                membership.getEmail(),
                membership.getAddress(),
                membership.isCorp()
        );
        log.info("registerMembership response: {}", membershipResponse);

        return new ResponseEntity<>(membershipResponse, null, HttpStatus.CREATED);
    }
}
