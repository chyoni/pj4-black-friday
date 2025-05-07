package cwchoiit.blackfriday.order.service.response;

public record StartOrderResponse(Long orderId, PaymentMethodReadResponse paymentMethod, String address) {
}
