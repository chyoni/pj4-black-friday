package cwchoiit.blackfriday.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    INITIATED,
    PAYMENT_COMPLETED,
    DELIVERY_REQUESTED,
}
