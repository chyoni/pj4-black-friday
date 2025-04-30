package cwchoiit.blackfriday.delivery.service.response;

import cwchoiit.blackfriday.delivery.entity.Delivery;
import cwchoiit.blackfriday.delivery.entity.DeliveryStatus;

public record DeliveryReadResponse(Long deliveryId,
                                   Long orderId,
                                   String address,
                                   String productName,
                                   Long productCount,
                                   DeliveryStatus status) {

    public static DeliveryReadResponse of(Delivery delivery) {
        return new DeliveryReadResponse(
                delivery.getDeliveryId(),
                delivery.getOrderId(),
                delivery.getAddress(),
                delivery.getProductName(),
                delivery.getProductCount(),
                delivery.getDeliveryStatus()
        );
    }
}
