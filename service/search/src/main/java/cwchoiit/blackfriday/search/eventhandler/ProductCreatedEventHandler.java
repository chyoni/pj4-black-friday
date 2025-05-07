package cwchoiit.blackfriday.search.eventhandler;

import cwchoiit.blackfriday.event.Event;
import cwchoiit.blackfriday.event.EventType;
import cwchoiit.blackfriday.event.payload.ProductCreatedEventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductCreatedEventHandler implements EventHandler<ProductCreatedEventPayload> {

    private final StringRedisTemplate redisTemplate;

    @Override
    public boolean isSupported(EventType eventType) {
        return eventType == EventType.PRODUCT_CREATED;
    }

    @Override
    public void handle(Event<ProductCreatedEventPayload> event) {
        log.debug("[handle] ProductCreatedEventPayload : {}", event.getPayload());
        event.getPayload().getTags().forEach(tag ->
                redisTemplate.opsForSet().add(
                        tag,
                        String.valueOf(event.getPayload().getProductId())
                )
        );
    }
}
