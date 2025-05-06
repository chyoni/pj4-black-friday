package cwchoiit.blackfriday.order.service.response;

import static cwchoiit.blackfriday.order.service.response.PaymentMethodReadResponse.*;

public record PaymentReadResponse(Long paymentId,
                                  Long memberId,
                                  Long orderId,
                                  Long amountKrw,
                                  PaymentMethodType methodType,
                                  String paymentPayload,
                                  PaymentStatus status,
                                  Long referenceCode) {

    public enum PaymentStatus {
        REQUESTED,
        COMPLETED,
        FAILED
    }
}

