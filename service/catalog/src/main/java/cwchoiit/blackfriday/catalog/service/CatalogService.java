package cwchoiit.blackfriday.catalog.service;

import cwchoiit.blackfriday.catalog.client.SearchClient;
import cwchoiit.blackfriday.catalog.entity.Product;
import cwchoiit.blackfriday.catalog.entity.SellerProduct;
import cwchoiit.blackfriday.catalog.repository.ProductRepository;
import cwchoiit.blackfriday.catalog.repository.SellerProductRepository;
import cwchoiit.blackfriday.catalog.service.request.CreateProductRequest;
import cwchoiit.blackfriday.catalog.service.response.CreateProductResponse;
import cwchoiit.blackfriday.catalog.service.response.ProductReadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogService {

    private final SellerProductRepository sellerProductRepository;
    private final ProductRepository productRepository;
    private final SearchClient searchClient;

    public CreateProductResponse createProduct(CreateProductRequest request) {
        SellerProduct sellerProduct = sellerProductRepository.save(SellerProduct.of(request.sellerId()));

        Product product = Product.of(
                sellerProduct.getProductId(),
                request.sellerId(),
                request.name(),
                request.description(),
                request.price(),
                request.stockCount(),
                request.tags()
        );

        searchClient.addTagCache(product.getProductId(), product.getTags());

        return CreateProductResponse.of(productRepository.save(product));
    }

    public void deleteProduct(Long productId) {
        searchClient.removeTagCache(
                productId,
                productRepository.findById(productId).orElseThrow().getTags()
        );
        productRepository.deleteById(productId);
        sellerProductRepository.deleteById(productId);
    }

    public List<ProductReadResponse> findAllProductsBySellerId(Long sellerId) {
        List<Long> productIds = sellerProductRepository.findAllBySellerId(sellerId).stream()
                .map(SellerProduct::getProductId)
                .toList();

        return productRepository.findAllByProductIdIn(productIds).stream()
                .map(ProductReadResponse::of)
                .toList();
    }

    public ProductReadResponse findProductById(Long productId) {
        return ProductReadResponse.of(productRepository.findById(productId).orElseThrow());
    }

    public ProductReadResponse decreaseStockCount(Long productId, Long count) {
        Product product = productRepository.findById(productId).orElseThrow();
        if (product.getStockCount() < count) {
            throw new IllegalArgumentException("Stock count is not enough");
        }

        product.decreaseStockCount(count);
        Product updatedProduct = productRepository.save(product);

        return ProductReadResponse.of(updatedProduct);
    }
}
