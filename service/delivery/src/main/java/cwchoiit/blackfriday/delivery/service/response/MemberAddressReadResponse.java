package cwchoiit.blackfriday.delivery.service.response;

import cwchoiit.blackfriday.delivery.entity.MemberAddress;

public record MemberAddressReadResponse(Long memberAddressId, Long memberId, String address, String alias) {

    public static MemberAddressReadResponse of(MemberAddress memberAddress) {
        return new MemberAddressReadResponse(
                memberAddress.getMemberAddressId(),
                memberAddress.getMemberId(),
                memberAddress.getAddress(),
                memberAddress.getAlias()
        );
    }
}
