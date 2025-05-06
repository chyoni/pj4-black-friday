package cwchoiit.blackfriday.payment.entity;

import lombok.Getter;
import lombok.ToString;

import static cwchoiit.blackfriday.exception.BlackFridayExCode.INVALID_PAYMENT_METHOD;

@Getter
@ToString
public enum PaymentMethodType {
    CREDIT_CARD,
    PAYPAL,
    KAKAO_PAY,
    NAVER_PAY,
    GOOGLE_PAY,
    APPLE_PAY;

    public static PaymentMethodType findBy(String paymentMethod) {
        for (PaymentMethodType value : values()) {
            if (value.name().equals(paymentMethod)) {
                return value;
            }
        }
        throw INVALID_PAYMENT_METHOD.build(paymentMethod);
    }

    public static PaymentMethodType findBy(PaymentMethodType paymentMethod) {
        for (PaymentMethodType value : values()) {
            if (value.equals(paymentMethod)) {
                return value;
            }
        }
        throw INVALID_PAYMENT_METHOD.build(paymentMethod);
    }
}
