package cwchoiit.blackfriday.payment.service.response;

import cwchoiit.blackfriday.payment.entity.Payment;
import cwchoiit.blackfriday.payment.entity.PaymentMethodType;
import cwchoiit.blackfriday.payment.entity.PaymentStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentReadResponse {
    private Long paymentId;
    private Long memberId;
    private Long orderId;
    private Long amountKrw;
    private PaymentMethodType methodType;
    private String paymentPayload;
    private PaymentStatus status;
    private Long referenceCode;

    public static PaymentReadResponse of(Payment payment) {
        PaymentReadResponse paymentReadResponse = new PaymentReadResponse();
        paymentReadResponse.paymentId = payment.getPaymentId();
        paymentReadResponse.memberId = payment.getMemberId();
        paymentReadResponse.orderId = payment.getOrderId();
        paymentReadResponse.amountKrw = payment.getAmountKrw();
        paymentReadResponse.methodType = payment.getMethodType();
        paymentReadResponse.paymentPayload = payment.getPaymentPayload();
        paymentReadResponse.status = payment.getPaymentStatus();
        paymentReadResponse.referenceCode = payment.getReferenceCode();
        return paymentReadResponse;
    }
}
