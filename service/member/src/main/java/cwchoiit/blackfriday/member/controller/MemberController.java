package cwchoiit.blackfriday.member.controller;

import cwchoiit.blackfriday.member.service.MemberService;
import cwchoiit.blackfriday.member.service.request.CreateMemberRequest;
import cwchoiit.blackfriday.member.service.request.UpdateMemberRequest;
import cwchoiit.blackfriday.member.service.response.MemberReadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/registration")
    public MemberReadResponse register(@RequestBody CreateMemberRequest request) {
        return memberService.create(request);
    }

    @PatchMapping("/{memberId}")
    public MemberReadResponse update(@PathVariable("memberId") Long memberId, @RequestBody UpdateMemberRequest request) {
        return memberService.update(memberId, request.changeUsername());
    }

    @GetMapping("/{loginId}/login")
    public MemberReadResponse login(@PathVariable("loginId") String loginId) {
        return memberService.findByLoginId(loginId);
    }
}
