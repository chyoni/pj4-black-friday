package cwchoiit.blackfriday.catalog.service.response;

import cwchoiit.blackfriday.catalog.entity.Product;

import java.util.List;

public record ProductReadResponse(Long productId,
                                  Long sellerId,
                                  String name,
                                  String description,
                                  Long price,
                                  Long stockCount,
                                  List<String> tags) {

    public static ProductReadResponse of(Product product) {
        return new ProductReadResponse(
                product.getProductId(),
                product.getSellerId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockCount(),
                product.getTags()
        );
    }
}
