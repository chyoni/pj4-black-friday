package cwchoiit.blackfriday.payment.service.request;

import cwchoiit.blackfriday.payment.entity.PaymentMethodType;

public record RegisterPaymentMethodRequest(Long memberId, PaymentMethodType methodType, String creditCardNumber) {
}
