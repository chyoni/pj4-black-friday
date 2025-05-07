package cwchoiit.blackfriday.event.payload.impl;

import cwchoiit.blackfriday.event.payload.EventPayload;
import lombok.*;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductRemovedEventPayload implements EventPayload {
    private Long productId;
    private List<String> tags;
}
