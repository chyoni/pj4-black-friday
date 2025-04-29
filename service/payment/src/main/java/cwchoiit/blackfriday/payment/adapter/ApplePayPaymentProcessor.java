package cwchoiit.blackfriday.payment.adapter;

import cwchoiit.blackfriday.payment.adapter.methodpayload.ApplePayPayload;
import cwchoiit.blackfriday.payment.entity.PaymentMethod;
import cwchoiit.blackfriday.payment.entity.PaymentMethodType;
import org.springframework.stereotype.Component;

@Component
public class ApplePayPaymentProcessor implements PaymentProcessor<ApplePayPayload> {
    @Override
    public boolean isSupported(PaymentMethodType methodType) {
        return PaymentMethodType.APPLE_PAY == methodType;
    }

    @Override
    public Long process(Long amountKrw, PaymentMethod personalPaymentMethod) {
        ApplePayPayload applePayPayload = makePayload(amountKrw, personalPaymentMethod);

        // actual process with external payment gateway
        return Math.round(Math.random() * 100000L);
    }

    @Override
    public ApplePayPayload makePayload(Long amountKrw, PaymentMethod personalPaymentMethod) {
        return new ApplePayPayload(amountKrw, "APP-WALLET-123");
    }
}
