package cwchoiit.blackfriday.payment.eventhandler;

import cwchoiit.blackfriday.event.Event;
import cwchoiit.blackfriday.event.EventType;
import cwchoiit.blackfriday.event.payload.impl.PaymentRequestEventPayload;
import cwchoiit.blackfriday.payment.service.PaymentService;
import cwchoiit.blackfriday.payment.service.request.ProcessPaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentRequestEventHandler implements EventHandler<PaymentRequestEventPayload> {

    private final PaymentService paymentService;

    @Override
    public boolean isSupported(EventType eventType) {
        return eventType == EventType.PAYMENT_REQUEST;
    }

    @Override
    public void handle(Event<PaymentRequestEventPayload> event) {
        PaymentRequestEventPayload payload = event.getPayload();
        paymentService.processPayment(new ProcessPaymentRequest(
                        payload.getMemberId(),
                        payload.getOrderId(),
                        payload.getAmountKrw(),
                        payload.getPaymentMethodId()
                )
        );
    }
}
