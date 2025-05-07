package cwchoiit.blackfriday.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@ToString
@Table(name = "product_order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_order_id")
    private Long productOrderId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "count")
    private Long count;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "delivery_id")
    private Long deliveryId;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    public static ProductOrder of(Long memberId,
                                  Long productId,
                                  Long count,
                                  OrderStatus status,
                                  Long paymentId,
                                  Long deliveryId,
                                  String deliveryAddress) {
        ProductOrder productOrder = new ProductOrder();
        productOrder.memberId = memberId;
        productOrder.productId = productId;
        productOrder.count = count;
        productOrder.status = status;
        productOrder.paymentId = paymentId;
        productOrder.deliveryId = deliveryId;
        productOrder.deliveryAddress = deliveryAddress;
        return productOrder;
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    public void updatePaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public void updateDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }
}
