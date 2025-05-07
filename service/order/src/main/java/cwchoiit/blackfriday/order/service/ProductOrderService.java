package cwchoiit.blackfriday.order.service;

import cwchoiit.blackfriday.event.payload.impl.DeliveryRequestEventPayload;
import cwchoiit.blackfriday.event.payload.impl.PaymentRequestEventPayload;
import cwchoiit.blackfriday.event.payload.impl.ProductDecreaseCountEventPayload;
import cwchoiit.blackfriday.order.client.CatalogClient;
import cwchoiit.blackfriday.order.client.DeliveryClient;
import cwchoiit.blackfriday.order.client.PaymentClient;
import cwchoiit.blackfriday.order.entity.OrderStatus;
import cwchoiit.blackfriday.order.entity.ProductOrder;
import cwchoiit.blackfriday.order.repository.ProductOrderRepository;
import cwchoiit.blackfriday.order.service.request.FinishOrderDeliveryRequest;
import cwchoiit.blackfriday.order.service.request.FinishOrderPaymentRequest;
import cwchoiit.blackfriday.order.service.request.ProcessOrderPaymentRequest;
import cwchoiit.blackfriday.order.service.request.StartOrderRequest;
import cwchoiit.blackfriday.order.service.response.*;
import cwchoiit.blackfriday.outbox.OutboxEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cwchoiit.blackfriday.event.EventType.*;
import static cwchoiit.blackfriday.exception.BlackFridayExCode.*;
import static cwchoiit.blackfriday.order.entity.OrderStatus.INITIATED;
import static cwchoiit.blackfriday.order.entity.OrderStatus.PAYMENT_COMPLETED;
import static cwchoiit.blackfriday.order.service.request.ProcessDeliveryRequest.DeliveryVendor.DELIVERY_LAB;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductOrderService {

    private final ProductOrderRepository productOrderRepository;
    private final CatalogClient catalogClient;
    private final DeliveryClient deliveryClient;
    private final PaymentClient paymentClient;
    private final OutboxEventPublisher outboxEventPublisher;

    @Transactional
    public StartOrderResponse startOrder(StartOrderRequest request) {
        catalogClient.getProduct(request.productId());

        PaymentMethodReadResponse findPaymentMethod = paymentClient.findPaymentMethodByMember(request.memberId())
                .getData().stream()
                .findFirst()
                .orElseThrow(() -> DOES_NOT_EXIST_PAYMENT_METHOD.build(request.memberId()));

        ProductOrder order = productOrderRepository.save(
                ProductOrder.of(
                        request.memberId(),
                        request.productId(),
                        request.count(),
                        INITIATED,
                        null,
                        null,
                        request.deliveryAddress()
                )
        );

        return new StartOrderResponse(order.getProductOrderId(), findPaymentMethod, request.deliveryAddress());
    }

    @Transactional
    public ProductOrderDetailReadResponse processOrderPayment(ProcessOrderPaymentRequest request) {
        ProductOrder productOrder = productOrderRepository.findById(request.orderId())
                .orElseThrow(() -> DOES_NOT_EXIST_PRODUCT_ORDER.build(request.orderId()));

        ProductReadResponse product = catalogClient.getProduct(productOrder.getProductId()).getData();

        outboxEventPublisher.publish(
                PAYMENT_REQUEST,
                new PaymentRequestEventPayload(
                        productOrder.getMemberId(),
                        request.orderId(),
                        product.price() * productOrder.getCount(),
                        request.paymentMethodId()
                )
        );

        return new ProductOrderDetailReadResponse(
                productOrder.getProductOrderId(),
                productOrder.getMemberId(),
                productOrder.getProductId(),
                productOrder.getPaymentId(),
                productOrder.getDeliveryId(),
                productOrder.getStatus(),
                null,
                null
        );
    }

    @Transactional
    public void finishOrderPayment(FinishOrderPaymentRequest request) {
        ProductOrder productOrder = productOrderRepository.findById(request.orderId())
                .orElseThrow(() -> DOES_NOT_EXIST_PRODUCT_ORDER.build(request.orderId()));

        productOrder.updatePaymentId(request.paymentId());
        productOrder.updateStatus(PAYMENT_COMPLETED);

        ProductReadResponse product = catalogClient.getProduct(productOrder.getProductId()).getData();

        outboxEventPublisher.publish(
                DELIVERY_REQUEST,
                new DeliveryRequestEventPayload(
                        request.orderId(),
                        product.name(),
                        productOrder.getCount(),
                        productOrder.getDeliveryAddress(),
                        DELIVERY_LAB.name()
                )
        );

        outboxEventPublisher.publish(
                PRODUCT_COUNT_DECREASE,
                new ProductDecreaseCountEventPayload(
                        productOrder.getProductId(),
                        productOrder.getCount()
                )
        );
    }

    @Transactional
    public void finishOrderDelivery(FinishOrderDeliveryRequest request) {
        ProductOrder productOrder = productOrderRepository.findById(request.orderId())
                .orElseThrow(() -> DOES_NOT_EXIST_PRODUCT_ORDER.build(request.orderId()));

        productOrder.updateDeliveryId(request.deliveryId());
        productOrder.updateStatus(OrderStatus.findByDeliveryStatus(request.deliveryStatus()));
    }

    public List<ProductOrderReadResponse> findOrdersByMemberId(Long memberId) {
        return productOrderRepository.findAllByMemberId(memberId).stream()
                .map(ProductOrderReadResponse::of)
                .toList();
    }

    public ProductOrderDetailReadResponse findOrderDetail(Long productOrderId) {
        ProductOrder productOrder = productOrderRepository.findById(productOrderId)
                .orElseThrow(() -> DOES_NOT_EXIST_PRODUCT_ORDER.build(productOrderId));
        PaymentReadResponse payment = paymentClient.findPayment(productOrder.getPaymentId()).getData();
        DeliveryReadResponse delivery = deliveryClient.findDelivery(productOrder.getDeliveryId()).getData();

        return new ProductOrderDetailReadResponse(
                productOrder.getProductOrderId(),
                productOrder.getMemberId(),
                productOrder.getProductId(),
                productOrder.getPaymentId(),
                productOrder.getDeliveryId(),
                productOrder.getStatus(),
                payment.status().name(),
                delivery.status().name()
        );
    }
}
