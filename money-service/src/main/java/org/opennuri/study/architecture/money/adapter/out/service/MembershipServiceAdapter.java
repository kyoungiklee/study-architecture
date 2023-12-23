package org.opennuri.study.architecture.money.adapter.out.service;

import lombok.RequiredArgsConstructor;
import org.opennuri.study.architecture.common.ServiceAdapter;
import org.opennuri.study.architecture.money.application.port.out.membership.MembershipServicePort;
import org.opennuri.study.architecture.money.application.port.out.membership.MembershipStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;


@ServiceAdapter
@RequiredArgsConstructor
public class MembershipServiceAdapter implements MembershipServicePort {
    private final RestTemplate restTemplate;

    @Value("${service.membership.url}")
    private String membershipServiceUrl;

    @Override
    public MembershipStatus getMembershipInfo(String membershipId) {
        AtomicReference<HttpHeaders> headers = new AtomicReference<>(new HttpHeaders());
        headers.get().setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        HttpEntity<String> entity = new HttpEntity<>(headers.get());

        String url = String.join("/", membershipServiceUrl, "membership", membershipId);
        Membership membership = restTemplate.getForObject(url, Membership.class, entity);

        assert membership != null;
        if(!membership.isValid()) {
            new MembershipStatus(membership.getMembershipId(), MembershipStatus.MembershipStatusType.SUSPENDED);
        }
        return new MembershipStatus(membership.getMembershipId(), MembershipStatus.MembershipStatusType.NORMAL);
    }
}
