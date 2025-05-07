package cwchoiit.blackfriday.order.service.request;

public record FinishOrderDeliveryRequest(Long deliveryId,
                                         Long orderId,
                                         String address,
                                         String productName,
                                         Long productCount,
                                         String deliveryStatus) {
}
