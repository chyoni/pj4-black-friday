package cwchoiit.blackfriday.event.payload.impl;

import cwchoiit.blackfriday.event.payload.EventPayload;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentResponseEventPayload implements EventPayload {
    private Long paymentId;
    private Long memberId;
    private Long orderId;
    private Long amountKrw;
    private String methodType;
    private String paymentPayload;
    private String status;
    private Long referenceCode;
}
