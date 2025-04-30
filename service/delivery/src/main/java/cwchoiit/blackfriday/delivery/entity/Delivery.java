package cwchoiit.blackfriday.delivery.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Entity
@Table(
        name = "delivery",
        indexes = {
                @Index(name = "idx_order_id", columnList = "order_id"),
                @Index(name = "idx_delivery_status", columnList = "delivery_status")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long deliveryId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_count")
    private Long productCount;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status")
    private DeliveryStatus deliveryStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_vendor")
    private DeliveryVendor deliveryVendor;

    @Column(name = "reference_code", unique = true)
    private Long referenceCode;

    public static Delivery create(Long orderId,
                                  String productName,
                                  Long productCount,
                                  String address,
                                  DeliveryVendor deliveryVendor,
                                  Long referenceCode) {
        Delivery delivery = new Delivery();
        delivery.orderId = orderId;
        delivery.productName = productName;
        delivery.productCount = productCount;
        delivery.address = address;
        delivery.deliveryStatus = DeliveryStatus.REQUESTED;
        delivery.deliveryVendor = deliveryVendor;
        delivery.referenceCode = referenceCode;
        return delivery;
    }

    public void updateDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
