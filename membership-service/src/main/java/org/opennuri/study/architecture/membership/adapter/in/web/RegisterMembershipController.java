package org.opennuri.study.architecture.membership.adapter.in.web;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.common.WebAdapter;
import org.opennuri.study.architecture.membership.appication.port.in.RegisterMembershipCommand;
import org.opennuri.study.architecture.membership.appication.port.in.RegisterMembershipUseCase;
import org.opennuri.study.architecture.membership.domain.Membership;
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
    Membership registerMembership(@RequestBody RegisterMembershipRequest request) {
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

        log.info(command.toString());
        return registerMembershipUseCase.resisterMembership(command);

    }
}
