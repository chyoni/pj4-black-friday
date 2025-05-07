package cwchoiit.blackfriday.order.eventhandler;

import cwchoiit.blackfriday.event.Event;
import cwchoiit.blackfriday.event.EventType;
import cwchoiit.blackfriday.event.payload.EventPayload;
import cwchoiit.blackfriday.event.payload.impl.DeliveryResponseEventPayload;
import cwchoiit.blackfriday.order.SpringBootTestContainerConfiguration;
import cwchoiit.blackfriday.order.client.CatalogClient;
import cwchoiit.blackfriday.order.client.DeliveryClient;
import cwchoiit.blackfriday.order.client.PaymentClient;
import cwchoiit.blackfriday.order.repository.ProductOrderRepository;
import cwchoiit.blackfriday.order.service.ProductOrderService;
import cwchoiit.blackfriday.order.service.request.FinishOrderDeliveryRequest;
import cwchoiit.blackfriday.outbox.OutboxEventPublisher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("EventHandler - DeliveryResponseEventHandler")
class DeliveryResponseEventHandlerTest extends SpringBootTestContainerConfiguration {

    @MockitoSpyBean
    ProductOrderRepository productOrderRepository;

    @MockitoBean
    CatalogClient catalogClient;

    @MockitoBean
    PaymentClient paymentClient;

    @MockitoBean
    DeliveryClient deliveryClient;

    @MockitoBean
    OutboxEventPublisher outboxEventPublisher;

    @MockitoBean
    ProductOrderService productOrderService;

    @Autowired
    List<EventHandler<? extends EventPayload>> eventHandlers;

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("DeliveryResponseEventHandler.handle() 검증")
    void handle() {
        DeliveryResponseEventPayload payload = new DeliveryResponseEventPayload(
                1L,
                1L,
                "경기 고양시",
                "",
                1L,
                ""
        );

        Event<EventPayload> event = Event.create(EventType.DELIVERY_RESPONSE, payload);

        for (EventHandler<? extends EventPayload> eventHandler : eventHandlers) {
            if (eventHandler.isSupported(event.getEventType())) {
                ((EventHandler<EventPayload>) eventHandler).handle(event);
            }
        }

        FinishOrderDeliveryRequest request = new FinishOrderDeliveryRequest(
                payload.getDeliveryId(),
                payload.getOrderId(),
                payload.getAddress(),
                payload.getProductName(),
                payload.getProductCount(),
                payload.getDeliveryStatus()
        );

        verify(productOrderService, times(1)).finishOrderDelivery(request);
    }
}