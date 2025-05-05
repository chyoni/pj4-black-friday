package cwchoiit.blackfriday.catalog.repository;

import cwchoiit.blackfriday.catalog.entity.Product;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CassandraRepository<Product, Long> {

    List<Product> findAllByProductIdIn(List<Long> productIds);
}
