package cwchoiit.blackfriday.member.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Entity - Member")
class MemberTest {

    @Test
    @DisplayName("Member 팩토리 메서드")
    void of() {
        Member member = Member.of("loginId", "username");
        assertThat(member).isNotNull();
        assertThat(member.getLoginId()).isEqualTo("loginId");
        assertThat(member.getUsername()).isEqualTo("username");
    }

    @Test
    @DisplayName("멤버이름 변경 메서드 호출 시 멤버이름이 변경된다.")
    void changePassword() {
        Member member = Member.of("loginId", "username");
        assertThat(member.getUsername()).isEqualTo("username");

        member.changeUsername("changeUsername");
        assertThat(member.getUsername()).isEqualTo("changeUsername");
    }
}