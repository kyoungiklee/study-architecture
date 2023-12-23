package org.opennuri.study.architecture.membership.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.opennuri.study.architecture.membership.adapter.out.persistence.SpringDataMembershipRepository;
import org.opennuri.study.architecture.membership.appication.port.in.FindMembershipCommand;
import org.opennuri.study.architecture.membership.appication.port.in.RegisterMembershipCommand;
import org.opennuri.study.architecture.membership.appication.service.FindMembershipService;
import org.opennuri.study.architecture.membership.appication.service.RegisterMembershipService;
import org.opennuri.study.architecture.membership.domain.Membership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Slf4j
@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(value = "test")
@DisplayName(value = "멤버십 컨트롤러 테스트")
class MembershipControllerTest {

    @Autowired
    private SpringDataMembershipRepository springDataMembershipRepository;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RegisterMembershipService registerMembershipService;
    @Autowired
    private FindMembershipService findMembershipService;

    private static MockMvc mockMvc;

    @BeforeAll
    static void beforeAll() {
        log.info("beforeAll");
    }

    @BeforeEach
    void setUp() {
        springDataMembershipRepository.deleteAll();
        mockMvc = MockMvcBuilders.webAppContextSetup(context) //.standaloneSetup(membershipController) 단건 테스트
                .addFilter(((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                })).build();
    }

    @Test
    @Order(1)
    @DisplayName(value = "멤버십 등록")
    void registerMembership() throws Exception {

        RegisterMembershipRequest request = RegisterMembershipRequest.builder()
                .name("name")
                .email("email")
                .address("address")
                .isCorp(true)
                .build();

        MembershipResponse membershipResponse = MembershipResponse.builder()
                .membershipId(1L)
                .name("name")
                .email("email")
                .address("address")
                .isCorp(true)
                .build();

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/membership/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(membershipResponse)));
    }

    @Test
    @Order(2)
    @DisplayName(value = "멤버십 조회")
    void findMembershipByMemberId() throws Exception {

        RegisterMembershipCommand command = RegisterMembershipCommand.builder()
                .name("lee kyoungik")
                .email("abc@gmail.com")
                .address("인천광역시 계양구 계산동 주부토로")
                .isCorp(false)
                .build();
        Membership membership = registerMembershipService.resisterMembership(command);
        log.info("membership: {}", membership);


        MembershipResponse membershipResponse = MembershipResponse.builder()
                .membershipId(membership.getMembershipId())
                .name("lee kyoungik")
                .email("abc@gmail.com")
                .address("인천광역시 계양구 계산동 주부토로")
                .isCorp(false)
                .build();

        log.info("membership: {}", membership);

        ///membership/findMembership/{membershipId}
        mockMvc.perform(MockMvcRequestBuilders.get("/membership/" + membership.getMembershipId())
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.membershipId").value(membership.getMembershipId()))
                .andExpect(jsonPath("$.name").value("lee kyoungik"))
                .andExpect(jsonPath("$.email").value("abc@gmail.com"))
                .andExpect(jsonPath("$.address").value("인천광역시 계양구 계산동 주부토로"))
                .andExpect(jsonPath("$.corp").value(false))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(membershipResponse)));
    }

    @Test
    @Order(3)
    @DisplayName(value = "멤버십 수정")
    void modifyMembership() throws Exception {

        //given
        RegisterMembershipCommand command = RegisterMembershipCommand.builder()
                .name("lee kyoungik")
                .email("abc@gmail.com")
                .address("인천광역시 계양구 계산동 주부토로")
                .isCorp(false)
                .build();
        Membership membership = registerMembershipService.resisterMembership(command);

        FindMembershipCommand findMembershipCommand = FindMembershipCommand.builder()
                .membershipId(membership.getMembershipId())
                .build();

        Membership findMembership = findMembershipService.findMembership(findMembershipCommand);
        log.info("findMembership: {}", findMembership);

        ModifyMembershipRequest modifyMembershipRequest = ModifyMembershipRequest.builder()
                .name("modified_name")
                .email("modified_email")
                .address("modified_address")
                .isValid(true)
                .isCorp(false)
                .build();

        MembershipResponse membershipResponse = MembershipResponse.builder()
                .membershipId(findMembership.getMembershipId())
                .name("modified_name")
                .email("modified_email")
                .address("modified_address")
                .isCorp(false)
                .build();

        ///membership/modify/{membershipId}
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/membership/" + findMembership.getMembershipId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(modifyMembershipRequest)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content()
                        .string(objectMapper.writeValueAsString(membershipResponse)));
    }

}