package org.opennuri.study.architecture.banking.adapter.out.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.banking.appication.port.out.GetMembershipPort;
import org.opennuri.study.architecture.banking.appication.port.out.MembershipStatus;
import org.opennuri.study.architecture.common.CommonHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpResponse;

@Slf4j
@Component
public class MembershipServiceAdapter implements GetMembershipPort {


    @Value("${service.membership.url}")
    private String membershipServiceUrl;
    private final CommonHttpClient httpClient;

    public MembershipServiceAdapter(CommonHttpClient httpClient){

        this.httpClient = httpClient;
    }
    @Override
    public MembershipStatus getMembership(String membershipId) {

        // hppt client 를 통해 membership service 에서 membershipId 를 통해 회원 정보를 가져온다.
        // 회원 정보가 없으면 null 을 리턴한다.

        String url = String.join("/", membershipServiceUrl, "membership", membershipId);

        try {
            HttpResponse<String> stringHttpResponse = httpClient.sendGetRequest(url);
            if (!(stringHttpResponse.statusCode() == 200)) {
                log.info("membership service error : {}", stringHttpResponse.body());
                return null;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            Membership membership = objectMapper.readValue(stringHttpResponse.body(), Membership.class);
            if(membership.isValid()) {
                return new MembershipStatus(membership.getMembershipId(), true);
            }else {
                return new MembershipStatus(membership.getMembershipId(), false);
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
