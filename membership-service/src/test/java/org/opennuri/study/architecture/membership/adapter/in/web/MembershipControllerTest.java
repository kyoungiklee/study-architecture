package org.opennuri.study.architecture.membership.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.opennuri.study.architecture.membership.domain.Membership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class MembershipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    void registerMembership() throws Exception {

        RegisterMembershipRequest request = RegisterMembershipRequest.builder()
                .name("name")
                .email("email")
                .address("address")
                .isCorp(true)
                .build();

        Membership membership = Membership.generateMember(
                new Membership.MembershipId("1")
                , new Membership.MembershipName("name")
                , new Membership.MembershipEmail("email")
                , new Membership.MembershipAddress("address")
                , new Membership.MembershipIsValid(true)
                , new Membership.MembershipIsCorp(true)
        );

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/membership/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(membership)));
    }

    @Test
    @Order(2)
    void findMembershipByMemberId() throws Exception {

        Membership membership = Membership.generateMember(
                new Membership.MembershipId("1")
                , new Membership.MembershipName("name")
                , new Membership.MembershipEmail("email")
                , new Membership.MembershipAddress("address")
                , new Membership.MembershipIsValid(true)
                , new Membership.MembershipIsCorp(true)
        );

        ///membership/findMembership/{membershipId}
        mockMvc.perform(MockMvcRequestBuilders.get("/membership/findMembership/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(membership)));
    }

    @Test
    @Order(3)
    void modifyMembership() throws Exception {

        ModifyMembershipRequest modifyMembershipRequest = ModifyMembershipRequest.builder()
                .name("modified_name")
                .email("modified_email")
                .address("modified_address")
                .isValid(true)
                .isCorp(false)
                .build();

        Membership membership = Membership.generateMember(
                new Membership.MembershipId("1")
                , new Membership.MembershipName("modified_name")
                , new Membership.MembershipEmail("modified_email")
                , new Membership.MembershipAddress("modified_address")
                , new Membership.MembershipIsValid(true)
                , new Membership.MembershipIsCorp(false)
        );

        ///membership/modify/{membershipId}
        mockMvc.perform(
                MockMvcRequestBuilders.post("/membership/modify/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyMembershipRequest)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(membership)));
    }

}