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
        // 뱅킹 시뮬레이션을 위해 항상 정상으로 전달함
        log.info("requestExternalFirmBanking : {}", request.toString());
        return new FirmBankingResult(FirmBankingResult.FirmBankingResultCode.SUCCESS, "정상");
    }
}
