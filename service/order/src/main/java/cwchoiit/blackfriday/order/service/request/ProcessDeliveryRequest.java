package cwchoiit.blackfriday.order.service.request;

public record ProcessDeliveryRequest(Long orderId,
                                     String productName,
                                     Long productCount,
                                     String address,
                                     DeliveryVendor vendor) {

    public enum DeliveryVendor {
        DELIVERY_LAB,
        FAST_DELIVERY
    }
}
