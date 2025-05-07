package cwchoiit.blackfriday.event;


import cwchoiit.blackfriday.event.payload.EventPayload;
import cwchoiit.blackfriday.serializer.Serializer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Event<T extends EventPayload> {
    private Long eventId;
    private EventType eventType;
    private T payload;

    public static Event<EventPayload> create(EventType eventType, EventPayload payload) {
        Event<EventPayload> event = new Event<>();
        event.eventId = UUID.randomUUID().getMostSignificantBits();
        event.eventType = eventType;
        event.payload = payload;
        return event;
    }

    public String toJson() {
        return Serializer.serialize(this);
    }

    public static Event<EventPayload> fromJson(String json) {
        RawEvent rawEvent = Serializer.deserialize(json, RawEvent.class);
        if (rawEvent == null) {
            return null;
        }

        EventType eType = EventType.from(rawEvent.eventType);
        return Event.create(eType, Serializer.deserialize(rawEvent.payload, eType.getPayloadClass()));
    }

    @Getter
    private static class RawEvent {
        private Long eventId;
        private String eventType;
        private Object payload;
    }
}
