package org.opennuri.study.architecture.money.adapter.in.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.common.exception.BusinessCheckFailException;
import org.opennuri.study.architecture.money.application.port.in.FindMemberMoneyUseCase;
import org.opennuri.study.architecture.money.domain.MemberMoney;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerErrorException;


@Slf4j
@RestController
@RequiredArgsConstructor
public class RequestMoneyFindController {

    private final FindMemberMoneyUseCase findMemberMoneyUseCase;

    @GetMapping(path = "/money/find/{membershipId}")
    public ResponseEntity<MemberMoneyResponse> findMoney(@PathVariable(value = "membershipId") String membershipId) {
        //membershipId 값이 null 인지 확인
        if (membershipId == null) {
            throw new IllegalArgumentException("membershipId is null");
        }
        //membershipId 값이 숫자인지 확인
        try {
            Long.parseLong(membershipId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("membershipId is not number");
        }
        //membershipId 값이 0 이거나 양수 인지 확인
        if (Long.parseLong(membershipId) < 0) {
            throw new IllegalArgumentException("membershipId is not 0 or positive");
        }

        //멤버쉽 money 조회 및 응답 처리
        MemberMoney memberMoney;
        try {
            memberMoney = findMemberMoneyUseCase.findMemberMoney(Long.parseLong(membershipId));
        } catch (BusinessCheckFailException e) {
            log.error("멤버쉽 money가 존재하지 않습니다. 멤버쉽 아이디: {}", membershipId, e);

            MemberMoneyResponse notFound = new MemberMoneyResponse(Long.parseLong(membershipId), 0L
                    , "멤버쉽 money가 존재하지 않습니다.");
            return new ResponseEntity<MemberMoneyResponse>(notFound, null, HttpStatus.NOT_FOUND);
        } catch (ServerErrorException e) {
            log.error("멤버쉽 money 조회 실패. 멤버쉽 아이디: {}", membershipId, e);

            MemberMoneyResponse serverError = new MemberMoneyResponse(Long.parseLong(membershipId), 0L
                    , "멤버쉽 money 조회 실패");
            return new ResponseEntity<MemberMoneyResponse>(serverError, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //멤버쉽 money 조회 성공 시 응답 처리
        MemberMoneyResponse memberMoneyResponse = new MemberMoneyResponse(
                memberMoney.getMembershipId(),
                memberMoney.getMoneyAmount(),
                "SUCCESS"
        );
        return ResponseEntity.ok(memberMoneyResponse);
    }
}
