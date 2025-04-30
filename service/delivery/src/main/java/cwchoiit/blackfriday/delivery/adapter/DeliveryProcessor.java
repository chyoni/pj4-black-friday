package cwchoiit.blackfriday.delivery.adapter;

import cwchoiit.blackfriday.delivery.entity.DeliveryVendor;
import cwchoiit.blackfriday.delivery.service.request.ProcessDeliveryRequest;

public interface DeliveryProcessor {
    boolean isSupported(DeliveryVendor vendor);

    Long process(ProcessDeliveryRequest request);
}
