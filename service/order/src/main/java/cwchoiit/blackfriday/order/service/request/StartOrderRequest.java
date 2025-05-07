package cwchoiit.blackfriday.order.service.request;

public record StartOrderRequest(Long memberId, Long productId, Long count, String deliveryAddress) {
}
