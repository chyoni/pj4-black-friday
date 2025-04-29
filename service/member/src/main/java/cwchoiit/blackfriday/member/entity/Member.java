package cwchoiit.blackfriday.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "login_id", unique = true)
    private String loginId;

    @Column(name = "username")
    private String username;

    public static Member of(String loginId, String username) {
        Member member = new Member();
        member.loginId = loginId;
        member.username = username;
        return member;
    }

    public void changeUsername(String changeUsername) {
        this.username = changeUsername;
    }
}
