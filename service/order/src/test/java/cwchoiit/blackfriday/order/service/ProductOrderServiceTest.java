package cwchoiit.blackfriday.order.service;

import cwchoiit.blackfriday.order.SpringBootTestContainerConfiguration;
import cwchoiit.blackfriday.order.client.CatalogClient;
import cwchoiit.blackfriday.order.client.DeliveryClient;
import cwchoiit.blackfriday.order.client.PaymentClient;
import cwchoiit.blackfriday.order.entity.OrderStatus;
import cwchoiit.blackfriday.order.entity.ProductOrder;
import cwchoiit.blackfriday.order.repository.ProductOrderRepository;
import cwchoiit.blackfriday.order.service.request.*;
import cwchoiit.blackfriday.order.service.response.*;
import cwchoiit.blackfriday.outbox.OutboxEventPublisher;
import cwchoiit.blackfriday.response.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.List;

import static cwchoiit.blackfriday.event.EventType.*;
import static cwchoiit.blackfriday.order.entity.OrderStatus.DELIVERY_COMPLETED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - ProductOrderService")
class ProductOrderServiceTest extends SpringBootTestContainerConfiguration {

    @Autowired
    ProductOrderService productOrderService;

    @MockitoSpyBean
    ProductOrderRepository productOrderRepository;

    @MockitoBean
    PaymentClient paymentClient;

    @MockitoBean
    DeliveryClient deliveryClient;

    @MockitoBean
    CatalogClient catalogClient;

    @MockitoBean
    OutboxEventPublisher outboxEventPublisher;

    @Test
    @DisplayName("startOrder() 검증")
    void startOrderTest() {
        PaymentMethodReadResponse paymentMethodReadResponse = new PaymentMethodReadResponse(
                1L,
                1L,
                PaymentMethodReadResponse.PaymentMethodType.CREDIT_CARD,
                "1234-5678-9012-3456"
        );

        when(paymentClient.findPaymentMethodByMember(1L))
                .thenReturn(ApiResponse.ok(List.of(paymentMethodReadResponse)));
        when(catalogClient.getProduct(1L)).thenReturn(any());

        StartOrderResponse startOrderResponse = productOrderService.startOrder(new StartOrderRequest(
                        1L,
                        1L,
                        1L,
                        "경기 고양시"
                )
        );

        assertThat(startOrderResponse).isNotNull();
        assertThat(startOrderResponse.orderId()).isNotNull();
        assertThat(startOrderResponse.paymentMethod().creditCardNumber()).isEqualTo("1234-5678-9012-3456");
        assertThat(startOrderResponse.address()).isEqualTo("경기 고양시");

        ProductOrder productOrder = productOrderRepository.findById(startOrderResponse.orderId())
                .orElseThrow();

        assertThat(productOrder.getStatus()).isEqualTo(OrderStatus.INITIATED);
    }

    @Test
    @DisplayName("processOrderPayment() 검증")
    void processOrderPaymentTest() {
        ProductOrder productOrder = ProductOrder.of(
                1L,
                1L,
                1L,
                OrderStatus.INITIATED,
                null,
                null,
                "경기 고양시"
        );
        productOrderRepository.save(productOrder);

        ProductReadResponse productReadResponse = new ProductReadResponse(
                1L,
                1L,
                "PD1",
                "PD1",
                10000L,
                10L,
                List.of("tv", "monitor")
        );

        when(catalogClient.getProduct(1L)).thenReturn(ApiResponse.ok(productReadResponse));

        ProductOrderDetailReadResponse response = productOrderService.processOrderPayment(new ProcessOrderPaymentRequest(
                        productOrder.getProductOrderId(),
                        1L,
                        1L,
                        ProcessDeliveryRequest.DeliveryVendor.DELIVERY_LAB
                )
        );
        assertThat(response.productId()).isEqualTo(1L);
        assertThat(response.productOrderId()).isEqualTo(productOrder.getProductOrderId());

        verify(outboxEventPublisher, times(1)).publish(eq(PAYMENT_REQUEST), any());
    }

    @Test
    @DisplayName("finishOrderPayment() 검증")
    void finishOrderPaymentTest() {
        ProductOrder productOrder = ProductOrder.of(
                1L,
                1L,
                1L,
                OrderStatus.INITIATED,
                null,
                null,
                "경기 고양시"
        );
        productOrderRepository.save(productOrder);

        ProductReadResponse productReadResponse = new ProductReadResponse(
                1L,
                1L,
                "PD1",
                "PD1",
                10000L,
                10L,
                List.of("tv", "monitor")
        );

        when(catalogClient.getProduct(1L)).thenReturn(ApiResponse.ok(productReadResponse));

        productOrderService.finishOrderPayment(new FinishOrderPaymentRequest(
                        300L,
                        productOrder.getMemberId(),
                        productOrder.getProductOrderId(),
                        10000L,
                        PaymentMethodReadResponse.PaymentMethodType.CREDIT_CARD.name(),
                        "",
                        "",
                        1L
                )
        );

        ProductOrder updatedProductOrder = productOrderRepository.findById(productOrder.getProductOrderId())
                .orElseThrow();

        assertThat(updatedProductOrder.getStatus()).isEqualTo(OrderStatus.PAYMENT_COMPLETED);
        assertThat(updatedProductOrder.getPaymentId()).isEqualTo(300L);

        verify(outboxEventPublisher, times(1)).publish(eq(DELIVERY_REQUEST), any());
        verify(outboxEventPublisher, times(1)).publish(eq(PRODUCT_COUNT_DECREASE), any());
    }

    @Test
    @DisplayName("finishOrderDelivery() 검증")
    void finishOrderDeliveryTest() {
        ProductOrder productOrder = ProductOrder.of(
                1L,
                1L,
                1L,
                OrderStatus.INITIATED,
                null,
                null,
                "경기 고양시"
        );
        productOrderRepository.save(productOrder);

        productOrderService.finishOrderDelivery(new FinishOrderDeliveryRequest(
                        10L,
                        productOrder.getProductOrderId(),
                        "경기 고양시",
                        "",
                        1L,
                        DeliveryReadResponse.DeliveryStatus.COMPLETED.name()
                )
        );

        ProductOrder updatedProductOrder = productOrderRepository.findById(productOrder.getProductOrderId())
                .orElseThrow();

        assertThat(updatedProductOrder.getDeliveryId()).isEqualTo(10L);
        assertThat(updatedProductOrder.getStatus()).isEqualTo(DELIVERY_COMPLETED);
    }


    @Test
    @DisplayName("findOrdersByMemberId() 검증")
    void findOrdersByMemberId() {
        ProductOrder productOrder = ProductOrder.of(
                1L,
                1L,
                1L,
                OrderStatus.INITIATED,
                null,
                null,
                "경기 고양시"
        );
        ProductOrder productOrder2 = ProductOrder.of(
                1L,
                2L,
                1L,
                OrderStatus.INITIATED,
                null,
                null,
                "경기 고양시"
        );
        productOrderRepository.save(productOrder);
        productOrderRepository.save(productOrder2);

        List<ProductOrderReadResponse> productOrderReadResponses = productOrderService.findOrdersByMemberId(1L);
        assertThat(productOrderReadResponses.size()).isEqualTo(2);
        assertThat(productOrderReadResponses.get(0).productOrderId()).isEqualTo(productOrder.getProductOrderId());
        assertThat(productOrderReadResponses.get(1).productOrderId()).isEqualTo(productOrder2.getProductOrderId());
    }

    @Test
    @DisplayName("findOrderDetail() 검증")
    void findOrderDetail() {
        ProductOrder productOrder = ProductOrder.of(
                1L,
                1L,
                1L,
                OrderStatus.INITIATED,
                null,
                null,
                "경기 고양시"
        );
        productOrderRepository.save(productOrder);

        PaymentReadResponse paymentReadResponse = new PaymentReadResponse(
                1L,
                1L,
                productOrder.getProductOrderId(),
                10000L,
                PaymentMethodReadResponse.PaymentMethodType.CREDIT_CARD,
                "",
                PaymentReadResponse.PaymentStatus.COMPLETED,
                1L
        );
        when(paymentClient.findPayment(productOrder.getPaymentId())).thenReturn(ApiResponse.ok(paymentReadResponse));

        DeliveryReadResponse deliveryReadResponse = new DeliveryReadResponse(
                1L,
                productOrder.getProductOrderId(),
                "경기 고양시",
                "",
                1L,
                DeliveryReadResponse.DeliveryStatus.COMPLETED
        );
        when(deliveryClient.findDelivery(productOrder.getDeliveryId())).thenReturn(ApiResponse.ok(deliveryReadResponse));

        ProductOrderDetailReadResponse response = productOrderService.findOrderDetail(productOrder.getProductOrderId());

        assertThat(response.productOrderId()).isEqualTo(productOrder.getProductOrderId());
        assertThat(response.paymentStatus()).isEqualTo(paymentReadResponse.status().name());
        assertThat(response.deliveryStatus()).isEqualTo(deliveryReadResponse.status().name());
    }
}