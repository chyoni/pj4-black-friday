package cwchoiit.blackfriday.order.entity;

import cwchoiit.blackfriday.exception.BlackFridayExCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    INITIATED,
    PAYMENT_COMPLETED,
    DELIVERY_REQUESTED,
    DELIVERY_IN_PROCESS,
    DELIVERY_FAILED,
    DELIVERY_COMPLETED;

    public static OrderStatus findBy(String orderStatus) {
        for (OrderStatus value : values()) {
            if (value.name().equals(orderStatus)) {
                return value;
            }
        }
        throw BlackFridayExCode.INVALID_PRODUCT_ORDER_STATUS.build(orderStatus);
    }

    public static OrderStatus findByDeliveryStatus(String deliveryStatus) {
        return switch (deliveryStatus) {
            case "REQUESTED" -> DELIVERY_REQUESTED;
            case "IN_DELIVERY" -> DELIVERY_IN_PROCESS;
            case "COMPLETED" -> DELIVERY_COMPLETED;
            case "FAILED" -> DELIVERY_FAILED;
            default -> throw BlackFridayExCode.INVALID_PRODUCT_ORDER_STATUS.build(deliveryStatus);
        };
    }
}
