package cwchoiit.blackfriday.delivery.eventhandler;

import cwchoiit.blackfriday.delivery.entity.DeliveryVendor;
import cwchoiit.blackfriday.delivery.service.DeliveryService;
import cwchoiit.blackfriday.delivery.service.request.ProcessDeliveryRequest;
import cwchoiit.blackfriday.event.Event;
import cwchoiit.blackfriday.event.EventType;
import cwchoiit.blackfriday.event.payload.impl.DeliveryRequestEventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryRequestEventHandler implements EventHandler<DeliveryRequestEventPayload> {

    private final DeliveryService deliveryService;

    @Override
    public boolean isSupported(EventType eventType) {
        return eventType == EventType.DELIVERY_REQUEST;
    }

    @Override
    public void handle(Event<DeliveryRequestEventPayload> event) {
        DeliveryRequestEventPayload payload = event.getPayload();
        deliveryService.processDelivery(new ProcessDeliveryRequest(
                        payload.getOrderId(),
                        payload.getProductName(),
                        payload.getProductCount(),
                        payload.getDeliveryAddress(),
                        DeliveryVendor.valueOf(payload.getVendor())
                )
        );
    }
}
