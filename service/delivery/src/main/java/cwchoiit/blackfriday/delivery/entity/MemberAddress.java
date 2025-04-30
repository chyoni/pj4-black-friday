package cwchoiit.blackfriday.delivery.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@ToString
@Table(
        name = "member_address",
        indexes = { @Index(name = "idx_member_id", columnList = "member_id") }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_address_id")
    private Long memberAddressId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "address")
    private String address;

    @Column(name = "alias")
    private String alias;

    public static MemberAddress of(Long memberId, String address, String alias) {
        MemberAddress memberAddress = new MemberAddress();
        memberAddress.memberId = memberId;
        memberAddress.address = address;
        memberAddress.alias = alias;
        return memberAddress;
    }

}
