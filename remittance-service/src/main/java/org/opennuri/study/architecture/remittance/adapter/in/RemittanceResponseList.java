package org.opennuri.study.architecture.remittance.adapter.in;

import lombok.NoArgsConstructor;

import java.util.List;

public class RemittanceResponseList {
    private List<RemittanceResponseVO> remittanceResponseList;

    public RemittanceResponseList(List<RemittanceResponseVO> remittanceResponseList) {
        this.remittanceResponseList = remittanceResponseList;
    }

    public List<RemittanceResponseVO> getRemittanceResponseList() {
        return remittanceResponseList;
    }

    public void setRemittanceResponseList(List<RemittanceResponseVO> remittanceResponseList) {
        this.remittanceResponseList = remittanceResponseList;
    }
}
