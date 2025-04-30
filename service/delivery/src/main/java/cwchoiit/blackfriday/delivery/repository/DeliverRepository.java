package cwchoiit.blackfriday.delivery.repository;

import cwchoiit.blackfriday.delivery.entity.Delivery;
import cwchoiit.blackfriday.delivery.entity.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliverRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findAllByOrderId(Long orderId);

    List<Delivery> findAllByDeliveryStatus(DeliveryStatus status);
}
