package cwchoiit.blackfriday.catalog.service;

import cwchoiit.blackfriday.catalog.entity.Product;
import cwchoiit.blackfriday.catalog.entity.SellerProduct;
import cwchoiit.blackfriday.catalog.repository.ProductRepository;
import cwchoiit.blackfriday.catalog.repository.SellerProductRepository;
import cwchoiit.blackfriday.catalog.service.request.CreateProductRequest;
import cwchoiit.blackfriday.catalog.service.response.CreateProductResponse;
import cwchoiit.blackfriday.catalog.service.response.ProductReadResponse;
import cwchoiit.blackfriday.event.payload.ProductCreatedEventPayload;
import cwchoiit.blackfriday.event.payload.ProductRemovedEventPayload;
import cwchoiit.blackfriday.outbox.OutboxEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cwchoiit.blackfriday.event.EventType.PRODUCT_CREATED;
import static cwchoiit.blackfriday.event.EventType.PRODUCT_REMOVED;
import static cwchoiit.blackfriday.exception.BlackFridayExCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CatalogService {

    private final SellerProductRepository sellerProductRepository;
    private final ProductRepository productRepository;
    private final OutboxEventPublisher outboxEventPublisher;

    @Transactional
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

        try {
            productRepository.save(product); // Cassandra
        } catch (Exception e) {
            throw PRODUCT_SAVE_FAILED.build();
        }

        outboxEventPublisher.publish(PRODUCT_CREATED, new ProductCreatedEventPayload(product.getProductId(), product.getTags()));

        return CreateProductResponse.of(product);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        sellerProductRepository.deleteById(productId);

        List<String> tags = productRepository.findById(productId)
                .orElseThrow(() -> DOES_NOT_EXIST_PRODUCT.build(productId))
                .getTags();

        try {
            productRepository.deleteById(productId); // Cassandra
        } catch (Exception e) {
            throw PRODUCT_REMOVED_FAILED.build();
        }

        outboxEventPublisher.publish(PRODUCT_REMOVED, new ProductRemovedEventPayload(productId, tags));
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
        return ProductReadResponse.of(
                productRepository.findById(productId)
                        .orElseThrow(() -> DOES_NOT_EXIST_PRODUCT.build(productId))
        );
    }

    public ProductReadResponse decreaseStockCount(Long productId, Long count) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> DOES_NOT_EXIST_PRODUCT.build(productId));

        if (product.getStockCount() < count) {
            throw INVALID_STOCK_COUNT.build(productId, product.getStockCount(), count);
        }

        product.decreaseStockCount(count);
        Product updatedProduct = productRepository.save(product);

        return ProductReadResponse.of(updatedProduct);
    }
}
