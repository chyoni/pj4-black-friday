package cwchoiit.blackfriday.payment.repository;

import cwchoiit.blackfriday.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
