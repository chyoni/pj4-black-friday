package cwchoiit.blackfriday.payment.service.request;

public record ProcessPaymentRequest(Long memberId, Long orderId, Long amountKrw, Long paymentMethodId) {
}
