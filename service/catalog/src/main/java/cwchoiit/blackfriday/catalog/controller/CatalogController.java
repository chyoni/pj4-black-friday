package cwchoiit.blackfriday.catalog.controller;

import cwchoiit.blackfriday.catalog.service.CatalogService;
import cwchoiit.blackfriday.catalog.service.request.CreateProductRequest;
import cwchoiit.blackfriday.catalog.service.response.CreateProductResponse;
import cwchoiit.blackfriday.catalog.service.response.ProductReadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    @PostMapping("/products")
    public CreateProductResponse createProduct(@RequestBody CreateProductRequest request) {
        return catalogService.createProduct(request);
    }

    @DeleteMapping("/products/{productId}")
    public void deleteProduct(@PathVariable("productId") Long productId) {
        catalogService.deleteProduct(productId);
    }

    @GetMapping("/products/{productId}")
    public ProductReadResponse findProductById(@PathVariable("productId") Long productId) {
        return catalogService.findProductById(productId);
    }

    @GetMapping("/sellers/{sellerId}/products")
    public List<ProductReadResponse> findAllProductsBySellerId(@PathVariable("sellerId") Long sellerId) {
        return catalogService.findAllProductsBySellerId(sellerId);
    }

    @PatchMapping("/products/{productId}/stock-count")
    public ProductReadResponse decreaseStockCount(@PathVariable("productId") Long productId,
                                                  @RequestParam("count") Long count) {
        return catalogService.decreaseStockCount(productId, count);
    }
}
