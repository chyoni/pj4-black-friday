package cwchoiit.blackfriday.delivery.service.request;

import cwchoiit.blackfriday.delivery.entity.DeliveryVendor;

public record ProcessDeliveryRequest(Long orderId,
                                     String productName,
                                     Long productCount,
                                     String address,
                                     DeliveryVendor vendor) {
}
