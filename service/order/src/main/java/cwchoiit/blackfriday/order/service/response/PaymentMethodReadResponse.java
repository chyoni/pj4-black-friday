package cwchoiit.blackfriday.order.service.response;

public record PaymentMethodReadResponse(Long methodId,
                                        Long memberId,
                                        PaymentMethodType methodType,
                                        String creditCardNumber) {

    public enum PaymentMethodType {
        CREDIT_CARD,
        PAYPAL,
        KAKAO_PAY,
        NAVER_PAY,
        GOOGLE_PAY,
        APPLE_PAY
    }
}
