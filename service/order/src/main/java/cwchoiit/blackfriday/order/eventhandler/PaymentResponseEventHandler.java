package cwchoiit.blackfriday.order.eventhandler;

import cwchoiit.blackfriday.event.Event;
import cwchoiit.blackfriday.event.EventType;
import cwchoiit.blackfriday.event.payload.impl.PaymentResponseEventPayload;
import cwchoiit.blackfriday.order.service.ProductOrderService;
import cwchoiit.blackfriday.order.service.request.FinishOrderPaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentResponseEventHandler implements EventHandler<PaymentResponseEventPayload> {

    private final ProductOrderService productOrderService;

    @Override
    public boolean isSupported(EventType eventType) {
        return eventType == EventType.PAYMENT_RESPONSE;
    }

    @Override
    public void handle(Event<PaymentResponseEventPayload> event) {
        PaymentResponseEventPayload payload = event.getPayload();
        productOrderService.finishOrderPayment(new FinishOrderPaymentRequest(
                        payload.getPaymentId(),
                        payload.getMemberId(),
                        payload.getOrderId(),
                        payload.getAmountKrw(),
                        payload.getMethodType(),
                        payload.getPaymentPayload(),
                        payload.getStatus(),
                        payload.getReferenceCode()
                )
        );
    }
}
