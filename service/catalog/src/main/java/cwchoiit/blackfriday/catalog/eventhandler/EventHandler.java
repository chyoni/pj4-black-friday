package cwchoiit.blackfriday.catalog.eventhandler;

import cwchoiit.blackfriday.event.Event;
import cwchoiit.blackfriday.event.EventType;
import cwchoiit.blackfriday.event.payload.EventPayload;

public interface EventHandler<T extends EventPayload> {
    boolean isSupported(EventType eventType);

    void handle(Event<T> event);
}
