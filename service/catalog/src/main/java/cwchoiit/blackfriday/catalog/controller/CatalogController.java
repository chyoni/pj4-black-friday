package cwchoiit.blackfriday.catalog.controller;

import cwchoiit.blackfriday.catalog.service.CatalogService;
import cwchoiit.blackfriday.catalog.service.request.CreateProductRequest;
import cwchoiit.blackfriday.catalog.service.response.CreateProductResponse;
import cwchoiit.blackfriday.catalog.service.response.ProductReadResponse;
import cwchoiit.blackfriday.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    @PostMapping("/products")
    public ResponseEntity<ApiResponse<CreateProductResponse>> createProduct(@RequestBody CreateProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(catalogService.createProduct(request)));
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId) {
        catalogService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<ProductReadResponse>> findProductById(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(ApiResponse.ok(catalogService.findProductById(productId)));
    }

    @GetMapping("/sellers/{sellerId}/products")
    public ResponseEntity<ApiResponse<List<ProductReadResponse>>> findAllProductsBySellerId(@PathVariable("sellerId") Long sellerId) {
        return ResponseEntity.ok(ApiResponse.ok(catalogService.findAllProductsBySellerId(sellerId)));
    }

    @PatchMapping("/products/{productId}/stock-count")
    public ResponseEntity<ApiResponse<ProductReadResponse>> decreaseStockCount(@PathVariable("productId") Long productId,
                                                                               @RequestParam("count") Long count) {
        return ResponseEntity.ok(ApiResponse.ok(catalogService.decreaseStockCount(productId, count)));
    }
}
