package cwchoiit.blackfriday.order.service.request;

public record FinishOrderPaymentRequest(Long paymentId,
                                        Long memberId,
                                        Long orderId,
                                        Long amountKrw,
                                        String methodType,
                                        String paymentPayload,
                                        String status,
                                        Long referenceCode) {
}
