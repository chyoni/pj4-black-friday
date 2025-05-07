package cwchoiit.blackfriday.event.payload.impl;

import cwchoiit.blackfriday.event.payload.EventPayload;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDecreaseCountEventPayload implements EventPayload {
    private Long productId;
    private Long decreaseCount;
}
