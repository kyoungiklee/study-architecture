package org.opennuri.study.architecture.remittance.application.port.out.banking;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BankingInfo {
    private String membershipId;
    private String bankName;
    private String bankAccountNumber;
    private boolean validLinkedStatus;

    @Override
    public String toString() {
        return "BankingInfo{" +
                "membershipId='" + membershipId + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", validLinkedStatus=" + validLinkedStatus +
                '}';
    }
}
