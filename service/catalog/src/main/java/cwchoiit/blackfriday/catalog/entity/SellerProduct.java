package cwchoiit.blackfriday.catalog.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@ToString
@Table(name = "seller_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SellerProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "seller_id")
    private Long sellerId;

    public static SellerProduct of(Long sellerId) {
        SellerProduct sellerProduct = new SellerProduct();
        sellerProduct.sellerId = sellerId;
        return sellerProduct;
    }
}
