package cwchoiit.blackfriday.payment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@ToString
@Table(
        name = "payment",
        indexes = { @Index(name = "idx_member_id", columnList = "member_id") }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "order_id", unique = true)
    private Long orderId;

    @Column(name = "amount_krw")
    private Long amountKrw;

    @Enumerated(EnumType.STRING)
    @Column(name = "method_type")
    private PaymentMethodType methodType;

    @Column(name = "payment_payload")
    private String paymentPayload;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "reference_code", unique = true)
    private Long referenceCode;

    public static Payment create(Long memberId,
                                 Long orderId,
                                 Long amountKrw,
                                 PaymentMethodType methodType,
                                 String paymentPayload,
                                 Long referenceCode) {
        Payment payment = new Payment();
        payment.memberId = memberId;
        payment.orderId = orderId;
        payment.amountKrw = amountKrw;
        payment.methodType = methodType;
        payment.paymentPayload = paymentPayload;
        payment.paymentStatus = PaymentStatus.REQUESTED;
        payment.referenceCode = referenceCode;
        return payment;
    }
}
