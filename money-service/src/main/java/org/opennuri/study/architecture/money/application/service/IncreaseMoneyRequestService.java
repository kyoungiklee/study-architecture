package org.opennuri.study.architecture.money.application.service;

import lombok.RequiredArgsConstructor;
import org.opennuri.study.architecture.common.UseCase;
import org.opennuri.study.architecture.money.application.port.in.IncreaseMoneyRequestCommand;
import org.opennuri.study.architecture.money.application.port.in.IncreaseMoneyRequestUseCase;
import org.opennuri.study.architecture.money.application.port.out.ChangeStatusPort;
import org.opennuri.study.architecture.money.application.port.out.IncreaseMoneyPort;
import org.opennuri.study.architecture.money.domain.ChangingMoneyRequest;
import org.opennuri.study.architecture.money.domain.ChangingMoneyRequestStatus;
import org.opennuri.study.architecture.money.domain.ChangingMoneyRequestType;

import java.time.LocalDateTime;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase {

    private final IncreaseMoneyPort increaseMoneyPort;
    private final ChangeStatusPort changeStatusPort;
    @Override
    public ChangingMoneyRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {
        //1. 고객 정보가 정상인지 확인 (회원)

        //2. 고객에게 연동된 계좌가 있는 지 확인, 연동된 계좌에 잔액이 충분한지 확인 (뱅킹))
        //3. 법인계좌의 상태가 정상인지 확인 (뱅킹)
        //4. 증액을 위한 충전 요청생성 및 기록 (머니)
        ChangingMoneyRequest changeMoneyRequest = increaseMoneyPort.createChangeMoneyRequest(
                new ChangingMoneyRequest.MembershipId(command.getMembershipId()),
                new ChangingMoneyRequest.RequestType(ChangingMoneyRequestType.DEPOSIT),
                new ChangingMoneyRequest.MoneyAmount(command.getMoneyAmount()),
                new ChangingMoneyRequest.RequestStatus(ChangingMoneyRequestStatus.REQUESTED),
                new ChangingMoneyRequest.RequestDateTime(LocalDateTime.now()),
                new ChangingMoneyRequest.UUID(UUID.randomUUID().toString())
        );
        //5. 펌뱅킹을 수행 (개인계좌 --> 법인계좌 이체) (뱅킹)
        //6. 성공시 증액 요청 상태를 완료로 변경(머니)
        ChangingMoneyRequest changeStatus =
                changeStatusPort.changeRequestStatus(changeMoneyRequest.getUuid(), ChangingMoneyRequestStatus.SUCCESS);

        //7-1. 멤버머니 잔액을 증액(머니)

        //7-2. 결과 실패시 실패로 증액요청 상태 변경 후 리턴

        return changeMoneyRequest;
    }
}
