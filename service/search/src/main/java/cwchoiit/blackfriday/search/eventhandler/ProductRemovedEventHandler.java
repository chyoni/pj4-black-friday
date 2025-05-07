package cwchoiit.blackfriday.search.eventhandler;

import cwchoiit.blackfriday.event.Event;
import cwchoiit.blackfriday.event.EventType;
import cwchoiit.blackfriday.event.payload.ProductRemovedEventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductRemovedEventHandler implements EventHandler<ProductRemovedEventPayload> {

    private final StringRedisTemplate redisTemplate;

    @Override
    public boolean isSupported(EventType eventType) {
        return eventType == EventType.PRODUCT_REMOVED;
    }

    @Override
    public void handle(Event<ProductRemovedEventPayload> event) {
        log.debug("[handle] ProductRemovedEventPayload : {}", event.getPayload());
        event.getPayload().getTags().forEach(tag ->
                redisTemplate.opsForSet().remove(
                        tag,
                        String.valueOf(event.getPayload().getProductId())
                )
        );
    }
}
