package cwchoiit.blackfriday.order.eventhandler;

import cwchoiit.blackfriday.event.Event;
import cwchoiit.blackfriday.event.EventType;
import cwchoiit.blackfriday.event.payload.impl.DeliveryResponseEventPayload;
import cwchoiit.blackfriday.order.service.ProductOrderService;
import cwchoiit.blackfriday.order.service.request.FinishOrderDeliveryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryResponseEventHandler implements EventHandler<DeliveryResponseEventPayload> {

    private final ProductOrderService productOrderService;

    @Override
    public boolean isSupported(EventType eventType) {
        return eventType == EventType.DELIVERY_RESPONSE;
    }

    @Override
    public void handle(Event<DeliveryResponseEventPayload> event) {
        DeliveryResponseEventPayload payload = event.getPayload();
        productOrderService.finishOrderDelivery(new FinishOrderDeliveryRequest(
                        payload.getDeliveryId(),
                        payload.getOrderId(),
                        payload.getAddress(),
                        payload.getProductName(),
                        payload.getProductCount(),
                        payload.getDeliveryStatus()
                )
        );
    }
}
