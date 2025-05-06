package cwchoiit.blackfriday.order.service.response;

import cwchoiit.blackfriday.order.entity.OrderStatus;
import cwchoiit.blackfriday.order.entity.ProductOrder;

public record ProductOrderReadResponse(Long productOrderId,
                                       Long memberId,
                                       Long productId,
                                       Long count,
                                       OrderStatus status,
                                       Long paymentId,
                                       Long deliveryId) {
    public static ProductOrderReadResponse of(ProductOrder productOrder) {
        return new ProductOrderReadResponse(
                productOrder.getProductOrderId(),
                productOrder.getMemberId(),
                productOrder.getProductId(),
                productOrder.getCount(),
                productOrder.getStatus(),
                productOrder.getPaymentId(),
                productOrder.getDeliveryId()
        );
    }
}
