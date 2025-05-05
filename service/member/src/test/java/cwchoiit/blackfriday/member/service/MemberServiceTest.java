package cwchoiit.blackfriday.member.service;

import cwchoiit.blackfriday.member.SpringBootTestConfiguration;
import cwchoiit.blackfriday.member.entity.Member;
import cwchoiit.blackfriday.member.repository.MemberRepository;
import cwchoiit.blackfriday.member.service.request.CreateMemberRequest;
import cwchoiit.blackfriday.member.service.response.MemberReadResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DisplayName("Service - MemberService")
class MemberServiceTest extends SpringBootTestConfiguration {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("멤버 생성 성공 케이스")
    void create_success() {
        CreateMemberRequest createMemberRequest = new CreateMemberRequest("loginId", "username");
        MemberReadResponse memberReadResponse = memberService.create(createMemberRequest);

        assertThat(memberReadResponse.memberId()).isNotNull();
        assertThat(memberReadResponse.loginId()).isEqualTo(createMemberRequest.loginId());
        assertThat(memberReadResponse.username()).isEqualTo(createMemberRequest.username());

        Member findMember = memberRepository.findById(memberReadResponse.memberId()).orElseThrow();

        assertThat(findMember.getMemberId()).isEqualTo(memberReadResponse.memberId());
        assertThat(findMember.getLoginId()).isEqualTo(createMemberRequest.loginId());
        assertThat(findMember.getUsername()).isEqualTo(createMemberRequest.username());
    }

    @Test
    @DisplayName("멤버 생성 실패 케이스 - 중복 아이디")
    void create_fail_duplicate_loginId() {
        CreateMemberRequest createMemberRequest = new CreateMemberRequest("loginId", "username");
        MemberReadResponse memberReadResponse = memberService.create(createMemberRequest);

        assertThat(memberReadResponse.memberId()).isNotNull();
        assertThat(memberReadResponse.loginId()).isEqualTo(createMemberRequest.loginId());
        assertThat(memberReadResponse.username()).isEqualTo(createMemberRequest.username());

        CreateMemberRequest createMemberRequest2 = new CreateMemberRequest("loginId", "username2");

        assertThatThrownBy(() -> memberService.create(createMemberRequest2))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("멤버 업데이트 성공 케이스")
    void update_success() {
        CreateMemberRequest createMemberRequest = new CreateMemberRequest("loginId", "username");
        MemberReadResponse memberReadResponse = memberService.create(createMemberRequest);

        assertThat(memberReadResponse.memberId()).isNotNull();
        assertThat(memberReadResponse.loginId()).isEqualTo(createMemberRequest.loginId());
        assertThat(memberReadResponse.username()).isEqualTo(createMemberRequest.username());

        Member findMember = memberRepository.findByLoginId(memberReadResponse.loginId()).orElseThrow();
        assertThat(findMember.getUsername()).isEqualTo(createMemberRequest.username());

        MemberReadResponse changeUsername = memberService.update(memberReadResponse.memberId(), "changeUsername");
        assertThat(changeUsername.memberId()).isEqualTo(memberReadResponse.memberId());
        assertThat(changeUsername.loginId()).isEqualTo(createMemberRequest.loginId());
        assertThat(changeUsername.username()).isEqualTo("changeUsername");

        Member findUpdateMember = memberRepository.findByLoginId(memberReadResponse.loginId()).orElseThrow();
        assertThat(findUpdateMember.getUsername()).isEqualTo("changeUsername");
    }

    @Test
    @DisplayName("멤버 업데이트 실패 케이스 - 없는 유저")
    void update_fail_not_found_member() {
        assertThatThrownBy(() -> memberService.update(1L, "changeUsername"))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("멈버 조회 성공 케이스")
    void findByLoginId() {
        CreateMemberRequest createMemberRequest = new CreateMemberRequest("loginId", "username");
        MemberReadResponse memberReadResponse = memberService.create(createMemberRequest);

        MemberReadResponse findMember = memberService.findByLoginId(memberReadResponse.loginId());
        assertThat(findMember.memberId()).isEqualTo(memberReadResponse.memberId());
        assertThat(findMember.loginId()).isEqualTo(createMemberRequest.loginId());
        assertThat(findMember.username()).isEqualTo(createMemberRequest.username());
    }

    @Test
    @DisplayName("멤버 조회 실패 케이스 - 없는 유저")
    void findByLoginId_fail_not_found_member() {
        assertThatThrownBy(() -> memberService.findByLoginId("not-found-loginId"))
                .isInstanceOf(NoSuchElementException.class);
    }
}