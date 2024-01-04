package org.opennuri.study.architecture.remittance.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.opennuri.study.architecture.common.BaseEntity;
import org.opennuri.study.architecture.remittance.common.RemittanceStatus;
import org.opennuri.study.architecture.remittance.common.RemittanceType;

@Entity
@Table(name = "remittance_request")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name = "remittance_request_seq", sequenceName = "remittance_request_seq", allocationSize = 1)
@Comment("송금요청")
public class RemittanceRequestJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "remittance_request_seq")
    @Comment("송금요청 아이디")
    @Column(nullable = false, unique = true, length = 20, columnDefinition = "bigint")
    private Long remittanceRequestId;
    @Comment("송금요청자 아이디")
    @Column(nullable = false)
    private Long senderId;
    @Comment("송금요청 수신자 아이디")
    private Long receiverId;
    @Comment("송금요청 은행 이름")
    private String toBankName;
    @Comment("송금요청 은행 계좌번호")
    private String toAccountNumber;
    @Comment("송금요청 타입 (내부고객, 외부은행)")
    @Column(nullable = false)

    // 송금요청 타입 (내부고객, 외부은행) 내부고객인 경우 receiverId가 필수이며, 외부은행인 경우 toBankName, toAccountNumber가 필수이다.
    @Enumerated(EnumType.STRING)
    private RemittanceType requestType;

    @Comment("송금요청 금액")
    @Column(nullable = false)
    private Long amount;
    @Comment("송금요청 설명")
    private String description;

    @Enumerated(EnumType.STRING)
    @Comment("송금요청 상태 (요청, 완료, 실패)")
    @Column(nullable = false)
    private RemittanceStatus requestStatus;
    @Comment("송금요청 uuid")
    @Column(nullable = false)
    private String uuid;
}
