package cwchoiit.blackfriday.event.payload.impl;

import cwchoiit.blackfriday.event.payload.EventPayload;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentRequestEventPayload implements EventPayload {
    private Long memberId;
    private Long orderId;
    private Long amountKrw;
    private Long paymentMethodId;
}
