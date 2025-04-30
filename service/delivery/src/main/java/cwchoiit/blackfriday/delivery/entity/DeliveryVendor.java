package cwchoiit.blackfriday.delivery.entity;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum DeliveryVendor {
    DELIVERY_LAB,
    FAST_DELIVERY;

    public static DeliveryVendor findBy(String deliveryVendor) {
        for (DeliveryVendor value : values()) {
            if (value.name().equals(deliveryVendor)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid delivery vendor: " + deliveryVendor);
    }
}
