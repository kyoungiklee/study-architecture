package org.opennuri.study.architecture.membership.adapter.out.persistence;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.opennuri.study.architecture.common.BaseEntity;

@Entity
@Table(name = "membership")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MembershipJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue
    private Long membershipId;
    private String name;
    private String email;
    private String address;
    private boolean isValid;
    private boolean isCorp;

    public MembershipJpaEntity(String name, String email, String address, boolean isValid, boolean isCorp) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.isValid = isValid;
        this.isCorp = isCorp;
    }

    @Override
    public String toString() {
        return "MembershipJpaEntity{" +
                "membershipId=" + membershipId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", isValid=" + isValid +
                ", isCorp=" + isCorp +
                '}';
    }
}
