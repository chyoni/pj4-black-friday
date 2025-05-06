package cwchoiit.blackfriday.payment.service;

import cwchoiit.blackfriday.payment.adapter.PaymentProcessor;
import cwchoiit.blackfriday.payment.entity.Payment;
import cwchoiit.blackfriday.payment.entity.PaymentMethod;
import cwchoiit.blackfriday.payment.entity.PaymentMethodType;
import cwchoiit.blackfriday.payment.repository.PaymentMethodRepository;
import cwchoiit.blackfriday.payment.repository.PaymentRepository;
import cwchoiit.blackfriday.payment.service.request.ProcessPaymentRequest;
import cwchoiit.blackfriday.payment.service.request.RegisterPaymentMethodRequest;
import cwchoiit.blackfriday.payment.service.response.PaymentMethodReadResponse;
import cwchoiit.blackfriday.payment.service.response.PaymentReadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cwchoiit.blackfriday.exception.BlackFridayExCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentRepository paymentRepository;
    private final List<PaymentProcessor<?>> paymentProcessors;

    @Transactional
    public PaymentMethodReadResponse registerPaymentMethod(RegisterPaymentMethodRequest request) {
        PaymentMethod createdPaymentMethod = paymentMethodRepository.save(
                PaymentMethod.of(
                        request.memberId(),
                        request.methodType(),
                        request.creditCardNumber()
                )
        );

        return new PaymentMethodReadResponse(
                createdPaymentMethod.getMethodId(),
                createdPaymentMethod.getMemberId(),
                createdPaymentMethod.getMethodType(),
                createdPaymentMethod.getCreditCardNumber()
        );
    }

    @Transactional
    public PaymentReadResponse processPayment(ProcessPaymentRequest request) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(request.paymentMethodId())
                .orElseThrow(() -> DOES_NOT_EXIST_PAYMENT_METHOD_BY_PAYMENT_METHOD_ID.build(request.paymentMethodId()));
        PaymentMethodType currentMethod = PaymentMethodType.findBy(paymentMethod.getMethodType());
        return PaymentReadResponse.of(processPay(request, currentMethod, paymentMethod));
    }

    public List<PaymentMethodReadResponse> findPaymentMethodByUser(Long memberId) {
        return paymentMethodRepository.findByMemberId(memberId).stream()
                .map(paymentMethod -> new PaymentMethodReadResponse(
                        paymentMethod.getMethodId(),
                        paymentMethod.getMemberId(),
                        paymentMethod.getMethodType(),
                        paymentMethod.getCreditCardNumber())
                ).toList();
    }

    public PaymentReadResponse findPayment(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .map(PaymentReadResponse::of)
                .orElseThrow(() -> DOES_NOT_EXIST_PAYMENT.build(paymentId));
    }

    private Payment processPay(ProcessPaymentRequest request,
                               PaymentMethodType currentMethod,
                               PaymentMethod paymentMethod) {
        for (PaymentProcessor<?> paymentProcessor : paymentProcessors) {
            if (paymentProcessor.isSupported(currentMethod)) {
                Long referenceCode = paymentProcessor.process(request.amountKrw(), paymentMethod);
                return paymentRepository.save(Payment.create(
                        request.memberId(),
                        request.orderId(),
                        request.amountKrw(),
                        currentMethod,
                        String.valueOf(paymentProcessor.makePayload(request.amountKrw(), paymentMethod)),
                        referenceCode
                ));
            }
        }
        throw UNSUPPORTED_PAYMENT_METHOD.build(currentMethod.name());
    }
}
