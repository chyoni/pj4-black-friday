package cwchoiit.blackfriday.payment.adapter;

import cwchoiit.blackfriday.payment.adapter.methodpayload.Payload;
import cwchoiit.blackfriday.payment.entity.PaymentMethod;
import cwchoiit.blackfriday.payment.entity.PaymentMethodType;

public interface PaymentProcessor<T extends Payload> {

    /**
     * {@link PaymentMethodType}를 처리할 수 있는지 확인
     * @param methodType {@link PaymentMethodType}
     * @return 처리할 수 있는 경우 {@code true}, 아니면 {@code false}
     */
    boolean isSupported(PaymentMethodType methodType);

    /**
     * 결제 진행 후 reference code (결제사에서 반환한) 반환
     * @param amountKrw 결제 금액
     * @param personalPaymentMethod {@link PaymentMethod}
     * @return reference code
     */
    Long process(Long amountKrw, PaymentMethod personalPaymentMethod);

    /**
     * 각 타입에 맞는 결제 요청 데이터 생성
     * @param amountKrw 결제 금액
     * @param personalPaymentMethod {@link PaymentMethod}
     * @return {@link Payload}
     */
    T makePayload(Long amountKrw, PaymentMethod personalPaymentMethod);
}
