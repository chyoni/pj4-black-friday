package cwchoiit.blackfriday.payment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "payment_method",
        indexes = { @Index(name = "idx_member_id", columnList = "member_id") }
)
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "method_id")
    private Long methodId;

    @Column(name = "member_id")
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "method_type")
    private PaymentMethodType methodType;

    @Column(name = "credit_card_number")
    private String creditCardNumber;

    public static PaymentMethod of(Long memberId, PaymentMethodType methodType, String creditCardNumber) {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.memberId = memberId;
        paymentMethod.methodType = methodType;
        paymentMethod.creditCardNumber = creditCardNumber;
        return paymentMethod;
    }
}
