package cwchoiit.blackfriday.member.service;

import cwchoiit.blackfriday.member.entity.Member;
import cwchoiit.blackfriday.member.repository.MemberRepository;
import cwchoiit.blackfriday.member.service.request.CreateMemberRequest;
import cwchoiit.blackfriday.member.service.response.MemberReadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cwchoiit.blackfriday.exception.BlackFridayExCode.DOES_NOT_EXIST_MEMBER;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberReadResponse create(CreateMemberRequest request) {
        Member createdMember = memberRepository.save(
                Member.of(
                        request.loginId(),
                        request.username()
                )
        );

        return new MemberReadResponse(createdMember.getMemberId(), createdMember.getLoginId(), createdMember.getUsername());
    }

    @Transactional
    public MemberReadResponse update(Long memberId, String changeUsername) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> DOES_NOT_EXIST_MEMBER.build(memberId));
        findMember.changeUsername(changeUsername);
        return new MemberReadResponse(findMember.getMemberId(), findMember.getLoginId(), findMember.getUsername());
    }

    public MemberReadResponse findByLoginId(String loginId) {
        Member findMember = memberRepository.findByLoginId(loginId).orElseThrow();
        return new MemberReadResponse(findMember.getMemberId(), findMember.getLoginId(), findMember.getUsername());
    }
}
