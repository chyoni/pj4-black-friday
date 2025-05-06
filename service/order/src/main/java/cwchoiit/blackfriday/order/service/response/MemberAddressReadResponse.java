package cwchoiit.blackfriday.order.service.response;

public record MemberAddressReadResponse(Long memberAddressId, Long memberId, String address, String alias) {
}
