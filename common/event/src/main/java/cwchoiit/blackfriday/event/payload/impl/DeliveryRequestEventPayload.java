package cwchoiit.blackfriday.event.payload.impl;

import cwchoiit.blackfriday.event.payload.EventPayload;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeliveryRequestEventPayload implements EventPayload {
    private Long orderId;
    private String productName;
    private Long productCount;
    private String deliveryAddress;
    private String vendor;
}
