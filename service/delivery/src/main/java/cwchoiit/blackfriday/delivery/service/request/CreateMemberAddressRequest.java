package cwchoiit.blackfriday.delivery.service.request;

public record CreateMemberAddressRequest(Long memberId, String address, String alias) {
}
