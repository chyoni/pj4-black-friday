package cwchoiit.blackfriday.event;

import cwchoiit.blackfriday.event.payload.EventPayload;
import cwchoiit.blackfriday.event.payload.ProductCreatedEventPayload;
import cwchoiit.blackfriday.event.payload.ProductRemovedEventPayload;
import cwchoiit.blackfriday.exception.BlackFridayExCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {
    PRODUCT_CREATED(ProductCreatedEventPayload.class, Topic.CATALOG_TO_SEARCH),
    PRODUCT_REMOVED(ProductRemovedEventPayload.class, Topic.CATALOG_TO_SEARCH);

    private final Class<? extends EventPayload> payloadClass;
    private final String topic;

    public static EventType from(String type) {
        for (EventType value : values()) {
            if (value.name().equals(type)) {
                return value;
            }
        }
        throw BlackFridayExCode.INVALID_EVENT_TYPE.build(type);
    }

    public static class Topic {
        public static final String CATALOG_TO_SEARCH = "catalog-to-search";
    }
}
