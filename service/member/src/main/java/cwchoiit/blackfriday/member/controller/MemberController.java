package cwchoiit.blackfriday.member.controller;

import cwchoiit.blackfriday.member.service.MemberService;
import cwchoiit.blackfriday.member.service.request.CreateMemberRequest;
import cwchoiit.blackfriday.member.service.request.UpdateMemberRequest;
import cwchoiit.blackfriday.member.service.response.MemberReadResponse;
import cwchoiit.blackfriday.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/registration")
    public ResponseEntity<ApiResponse<MemberReadResponse>> register(@RequestBody CreateMemberRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(memberService.create(request)));
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberReadResponse>> update(@PathVariable("memberId") Long memberId,
                                                                  @RequestBody UpdateMemberRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(memberService.update(memberId, request.changeUsername())));
    }

    @GetMapping("/{loginId}/login")
    public ResponseEntity<ApiResponse<MemberReadResponse>> login(@PathVariable("loginId") String loginId) {
        return ResponseEntity.ok(ApiResponse.ok(memberService.findByLoginId(loginId)));
    }
}
