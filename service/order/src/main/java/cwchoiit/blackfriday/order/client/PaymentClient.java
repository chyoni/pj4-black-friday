package cwchoiit.blackfriday.order.client;

import cwchoiit.blackfriday.order.service.request.ProcessPaymentRequest;
import cwchoiit.blackfriday.order.service.response.PaymentMethodReadResponse;
import cwchoiit.blackfriday.order.service.response.PaymentReadResponse;
import jakarta.annotation.PostConstruct;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentClient {

    private RestClient restClient;

    @Value("${client.payment-service.url}")
    private String paymentUrl;

    @PostConstruct
    public void init() {
        restClient = RestClient.create(paymentUrl);
    }

    public List<PaymentMethodReadResponse> findPaymentMethodByMember(Long memberId) {
        return restClient.get()
                .uri("/api/v1/payment/methods/members/{memberId}", memberId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public PaymentReadResponse processPayment(Long memberId, Long orderId, Long amountKrw, Long paymentMethodId) {
        return restClient.post()
                .uri("/api/v1/payment/process-payment")
                .body(new ProcessPaymentRequest(memberId, orderId, amountKrw, paymentMethodId))
                .retrieve()
                .body(PaymentReadResponse.class);
    }

    public PaymentReadResponse findPayment(Long paymentId) {
        return restClient.get()
                .uri("/api/v1/payment/{paymentId}", paymentId)
                .retrieve()
                .body(PaymentReadResponse.class);
    }
}
