package cwchoiit.blackfriday.catalog.entity;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;

@Getter
@ToString
@Table("product")
public class Product {

    @PrimaryKey
    private Long productId;

    @Column
    private Long sellerId;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Long price;

    @Column
    private Long stockCount;

    @Column
    private List<String> tags;

    public static Product of(Long productId,
                             Long sellerId,
                             String name,
                             String description,
                             Long price,
                             Long stockCount,
                             List<String> tags) {
        Product product = new Product();
        product.productId = productId;
        product.sellerId = sellerId;
        product.name = name;
        product.description = description;
        product.price = price;
        product.stockCount = stockCount;
        product.tags = tags;
        return product;
    }

    public void increaseStockCount(Long count) {
        stockCount = stockCount + count;
    }

    public void decreaseStockCount(Long count) {
        stockCount = stockCount - count;
    }
}
