package cwchoiit.blackfriday.order.service.response;

import cwchoiit.blackfriday.order.entity.OrderStatus;

public record ProductOrderDetailReadResponse(Long productOrderId,
                                             Long memberId,
                                             Long productId,
                                             Long paymentId,
                                             Long deliveryId,
                                             OrderStatus orderStatus,
                                             String paymentStatus,
                                             String deliveryStatus) {
}
