package cwchoiit.blackfriday.payment.adapter;

import cwchoiit.blackfriday.payment.adapter.methodpayload.CreditCardPayload;
import cwchoiit.blackfriday.payment.entity.PaymentMethod;
import cwchoiit.blackfriday.payment.entity.PaymentMethodType;
import org.springframework.stereotype.Component;

@Component
public class CreditCardPaymentProcessor implements PaymentProcessor<CreditCardPayload> {
    @Override
    public boolean isSupported(PaymentMethodType methodType) {
        return PaymentMethodType.CREDIT_CARD == methodType;
    }

    @Override
    public Long process(Long amountKrw, PaymentMethod personalPaymentMethod) {
        CreditCardPayload creditCardPayload = makePayload(amountKrw, personalPaymentMethod);
        // actual process with external payment gateway

        return Math.round(Math.random() * 100000L);
    }

    @Override
    public CreditCardPayload makePayload(Long amountKrw, PaymentMethod personalPaymentMethod) {
        return new CreditCardPayload(amountKrw, "1234-5678-9012-3456");
    }
}
