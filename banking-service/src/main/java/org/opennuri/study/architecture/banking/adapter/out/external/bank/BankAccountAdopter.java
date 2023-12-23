package org.opennuri.study.architecture.banking.adapter.out.external.bank;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opennuri.study.architecture.banking.appication.port.out.RequestBankAccountInfoPort;
import org.opennuri.study.architecture.banking.appication.port.out.RequestExternalFirmBankingPort;
import org.opennuri.study.architecture.common.ExternalSystem;

@Slf4j
@ExternalSystem
@RequiredArgsConstructor
public class BankAccountAdopter implements RequestBankAccountInfoPort, RequestExternalFirmBankingPort
{
    @Override
    public BankAccount getBankAccountInfo(GetBankAccountInfoRequest getBankAccountInfoRequest) {

        // 뱅킹 시뮬레이션을 위해 항상 정상인 계좌로 전달함
        return BankAccount.builder()
                .bankName(getBankAccountInfoRequest.getBankName())
                .bankAccountNUmber(getBankAccountInfoRequest.getBankAccountNumber())
                .isValid(true)
                .build();
    }

    @Override
    public FirmBankingResult requestExternalFirmBanking(ExternalFirmBankingRequest request) {
        log.info("requestExternalFirmBanking : {}", request.toString());
        // 뱅킹 시뮬레이션을 위해 항상 정상으로 전달함
        // firmbanking-service로 이체 요청을 하여 볍인계좌로 정상적으로 이체 되었다고 가정함
        boolean isSuccess = requestFirmBankingTransfer(request);

        if(!isSuccess) {
            return new FirmBankingResult(
                    request.getMembershipId()
                    , request.getMoneyAmount()
                    , FirmBankingResult.FirmBankingResultCode.FAIL
                    , "법인계좌로 이체에 실패하였습니다." );
        }

        return new FirmBankingResult(
                request.getMembershipId()
                , request.getMoneyAmount()
                , FirmBankingResult.FirmBankingResultCode.SUCCESS
                , "법인계좌로 이체에 성공하였습니다.");
    }

    private boolean requestFirmBankingTransfer(ExternalFirmBankingRequest request) {
        // firmbanking-service로 이체 요청을 하여 볍이계좌로 정상적으로 이체 되었음
        try {
            Thread.sleep(1000);
            log.info("requestFirmBankingTransfer success : {}", request.toString());
        } catch (InterruptedException e) {
            log.error("requestFirmBankingTransfer : {}", e.getMessage());
        }
        return true;
    }
}
