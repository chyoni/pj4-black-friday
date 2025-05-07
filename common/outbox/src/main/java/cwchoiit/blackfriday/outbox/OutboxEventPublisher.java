package cwchoiit.blackfriday.outbox;

import cwchoiit.blackfriday.event.Event;
import cwchoiit.blackfriday.event.EventType;
import cwchoiit.blackfriday.event.payload.EventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public void publish(EventType eventType, EventPayload payload) {
        eventPublisher.publishEvent(
                Outbox.of(
                        eventType,
                        Event.create(eventType, payload).toJson()
                )
        );
    }
}
