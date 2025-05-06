package cwchoiit.blackfriday.order.service.response;

public record DeliveryReadResponse(Long deliveryId,
                                   Long orderId,
                                   String address,
                                   String productName,
                                   Long productCount,
                                   DeliveryStatus status) {

    public enum DeliveryStatus {
        REQUESTED,
        IN_DELIVERY,
        COMPLETED,
        FAILED
    }
}
