package cwchoiit.blackfriday.event.payload.impl;

import cwchoiit.blackfriday.event.payload.EventPayload;
import lombok.*;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductCreatedEventPayload implements EventPayload {
    private Long productId;
    private List<String> tags;
}
