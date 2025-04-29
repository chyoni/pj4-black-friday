package cwchoiit.blackfriday.payment.service.response;

import cwchoiit.blackfriday.payment.entity.PaymentMethodType;

public record PaymentMethodReadResponse(Long methodId, Long memberId, PaymentMethodType methodType, String creditCardNumber) {
}
