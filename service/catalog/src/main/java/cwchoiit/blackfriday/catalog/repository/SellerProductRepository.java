package cwchoiit.blackfriday.catalog.repository;

import cwchoiit.blackfriday.catalog.entity.SellerProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerProductRepository extends JpaRepository<SellerProduct, Long> {

    List<SellerProduct> findAllBySellerId(Long sellerId);
}
