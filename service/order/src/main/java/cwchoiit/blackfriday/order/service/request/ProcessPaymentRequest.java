package cwchoiit.blackfriday.order.service.request;

public record ProcessPaymentRequest(Long memberId, Long orderId, Long amountKrw, Long paymentMethodId) {
}
