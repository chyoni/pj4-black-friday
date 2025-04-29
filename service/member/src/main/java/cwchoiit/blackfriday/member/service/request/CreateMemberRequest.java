package cwchoiit.blackfriday.member.service.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateMemberRequest {
    private String loginId;
    private String username;
}
