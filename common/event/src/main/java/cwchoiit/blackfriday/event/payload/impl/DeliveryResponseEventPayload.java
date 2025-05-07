package cwchoiit.blackfriday.event.payload.impl;

import cwchoiit.blackfriday.event.payload.EventPayload;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeliveryResponseEventPayload implements EventPayload {
    private Long deliveryId;
    private Long orderId;
    private String address;
    private String productName;
    private Long productCount;
    private String deliveryStatus;
}
