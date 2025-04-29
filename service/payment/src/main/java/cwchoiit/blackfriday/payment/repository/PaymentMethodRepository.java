package cwchoiit.blackfriday.payment.repository;

import cwchoiit.blackfriday.payment.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    List<PaymentMethod> findByMemberId(Long memberId);
}
