package cwchoiit.blackfriday.search.consumer;

import cwchoiit.blackfriday.event.Event;
import cwchoiit.blackfriday.event.payload.EventPayload;
import cwchoiit.blackfriday.search.eventhandler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

import static cwchoiit.blackfriday.event.EventType.Topic.CATALOG_TO_SEARCH;

@Slf4j
@Component
@RequiredArgsConstructor
public class CatalogToSearchEventConsumer {
    private final List<EventHandler<? extends EventPayload>> eventHandlers;

    @KafkaListener(topics = {CATALOG_TO_SEARCH})
    public void listen(String message, Acknowledgment ack) {
        log.info("[listen] Message received from [catalog-to-search] topic : {}", message);
        Event<EventPayload> event = Event.fromJson(message);
        if (event != null) {
            processHandleEvent(event);
        }
        ack.acknowledge();
    }

    @SuppressWarnings("unchecked")
    private void processHandleEvent(Event<EventPayload> event) {
        for (EventHandler<? extends EventPayload> eventHandler : eventHandlers) {
            if (eventHandler.isSupported(event.getEventType())) {
                ((EventHandler<EventPayload>) eventHandler).handle(event);
            }
        }
    }
}
