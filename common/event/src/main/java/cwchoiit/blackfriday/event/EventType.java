package cwchoiit.blackfriday.event;

import cwchoiit.blackfriday.event.payload.*;
import cwchoiit.blackfriday.event.payload.impl.*;
import cwchoiit.blackfriday.exception.BlackFridayExCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {
    PRODUCT_CREATED(ProductCreatedEventPayload.class, Topic.CATALOG_TO_SEARCH),
    PRODUCT_REMOVED(ProductRemovedEventPayload.class, Topic.CATALOG_TO_SEARCH),
    PRODUCT_COUNT_DECREASE(ProductDecreaseCountEventPayload.class, Topic.ORDER_TO_CATALOG),

    PAYMENT_REQUEST(PaymentRequestEventPayload.class, Topic.ORDER_TO_PAYMENT),
    PAYMENT_RESPONSE(PaymentResponseEventPayload.class, Topic.ORDER_TO_PAYMENT),

    DELIVERY_REQUEST(DeliveryRequestEventPayload.class, Topic.ORDER_TO_DELIVERY),
    DELIVERY_RESPONSE(DeliveryResponseEventPayload.class, Topic.ORDER_TO_DELIVERY);

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
        public static final String ORDER_TO_PAYMENT = "order-to-payment";
        public static final String ORDER_TO_DELIVERY = "order-to-delivery";
        public static final String ORDER_TO_CATALOG = "order-to-catalog";
    }
}
