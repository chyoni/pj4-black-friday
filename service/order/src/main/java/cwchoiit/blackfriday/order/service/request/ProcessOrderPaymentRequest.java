package cwchoiit.blackfriday.order.service.request;

import static cwchoiit.blackfriday.order.service.request.ProcessDeliveryRequest.*;

public record ProcessOrderPaymentRequest(Long orderId, Long paymentMethodId, Long addressId, DeliveryVendor vendor) {
}
