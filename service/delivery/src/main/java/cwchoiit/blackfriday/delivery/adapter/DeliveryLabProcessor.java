package cwchoiit.blackfriday.delivery.adapter;

import cwchoiit.blackfriday.delivery.entity.DeliveryVendor;
import cwchoiit.blackfriday.delivery.service.request.ProcessDeliveryRequest;
import org.springframework.stereotype.Component;

@Component
public class DeliveryLabProcessor implements DeliveryProcessor {
    @Override
    public boolean isSupported(DeliveryVendor vendor) {
        return vendor == DeliveryVendor.DELIVERY_LAB;
    }

    @Override
    public Long process(ProcessDeliveryRequest request) {
        // actual process with external delivery vendor
        return Math.round(Math.random() * 100000L);
    }
}
