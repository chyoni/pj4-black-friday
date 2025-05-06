package cwchoiit.blackfriday.order.service;

import cwchoiit.blackfriday.order.client.CatalogClient;
import cwchoiit.blackfriday.order.client.DeliveryClient;
import cwchoiit.blackfriday.order.client.PaymentClient;
import cwchoiit.blackfriday.order.entity.OrderStatus;
import cwchoiit.blackfriday.order.entity.ProductOrder;
import cwchoiit.blackfriday.order.repository.ProductOrderRepository;
import cwchoiit.blackfriday.order.service.request.FinishOrderRequest;
import cwchoiit.blackfriday.order.service.request.ProcessDeliveryRequest;
import cwchoiit.blackfriday.order.service.request.StartOrderRequest;
import cwchoiit.blackfriday.order.service.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductOrderService {

    private final ProductOrderRepository productOrderRepository;
    private final CatalogClient catalogClient;
    private final DeliveryClient deliveryClient;
    private final PaymentClient paymentClient;

    @Transactional
    public StartOrderResponse startOrder(StartOrderRequest request) {
        catalogClient.getProduct(request.productId());

        PaymentMethodReadResponse findPaymentMethod = paymentClient.findPaymentMethodByMember(request.memberId())
                .stream()
                .findFirst()
                .orElseThrow();

        MemberAddressReadResponse findMemberAddress = deliveryClient.findAllMemberAddressByMemberId(request.memberId())
                .stream()
                .findFirst()
                .orElseThrow();

        ProductOrder order = productOrderRepository.save(
                ProductOrder.of(
                        request.memberId(),
                        request.productId(),
                        request.count(),
                        OrderStatus.INITIATED,
                        null,
                        null
                )
        );

        return new StartOrderResponse(order.getProductOrderId(), findPaymentMethod, findMemberAddress);
    }

    @Transactional
    public ProductOrderDetailReadResponse finishOrder(FinishOrderRequest request) {
        ProductOrder productOrder = productOrderRepository.findById(request.orderId()).orElseThrow();

        ProductReadResponse product = catalogClient.getProduct(productOrder.getProductId());

        PaymentReadResponse payment = paymentClient.processPayment(
                productOrder.getMemberId(),
                request.orderId(),
                product.price() * productOrder.getCount(),
                request.paymentMethodId()
        );

        MemberAddressReadResponse memberAddress = deliveryClient.findMemberAddress(request.addressId());

        DeliveryReadResponse delivery = deliveryClient.processDelivery(
                request.orderId(),
                product.name(),
                productOrder.getCount(),
                memberAddress.address(),
                request.vendor());

        catalogClient.decreaseStockCount(productOrder.getProductId(), productOrder.getCount());

        productOrder.updateDeliveryId(delivery.deliveryId());
        productOrder.updatePaymentId(payment.paymentId());
        productOrder.updateStatus(OrderStatus.DELIVERY_REQUESTED);

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

    public List<ProductOrderReadResponse> findOrdersByMemberId(Long memberId) {
        return productOrderRepository.findAllByMemberId(memberId).stream()
                .map(ProductOrderReadResponse::of)
                .toList();
    }

    public ProductOrderDetailReadResponse findOrderDetail(Long productOrderId) {
        ProductOrder productOrder = productOrderRepository.findById(productOrderId).orElseThrow();
        PaymentReadResponse payment = paymentClient.findPayment(productOrder.getPaymentId());
        DeliveryReadResponse delivery = deliveryClient.findDelivery(productOrder.getDeliveryId());

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
