package org.opennuri.study.architecture.remittance.adapter.out.service.membership;

import lombok.RequiredArgsConstructor;
import org.opennuri.study.architecture.common.ServiceAdapter;
import org.opennuri.study.architecture.remittance.application.port.out.membership.MembershipServicePort;
import org.opennuri.study.architecture.remittance.application.port.out.membership.MembershipInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@ServiceAdapter
@RequiredArgsConstructor
public class MembershipServiceAdapter implements MembershipServicePort {

    private final RestTemplate restTemplate;

    @Value("${service.membership.url}")
    private String membershipUrl;

    @Override
    public MembershipInfo getMembershipStatus(String membershipId) {
        String url = String.join("/", membershipUrl, "membership", membershipId);
        return restTemplate.getForObject(url, MembershipInfo.class);
    }
}
