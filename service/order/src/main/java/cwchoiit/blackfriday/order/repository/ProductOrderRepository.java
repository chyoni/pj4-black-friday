package cwchoiit.blackfriday.order.repository;

import cwchoiit.blackfriday.order.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

    List<ProductOrder> findAllByMemberId(Long memberId);
}
