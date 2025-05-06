package cwchoiit.blackfriday.order.client;

import cwchoiit.blackfriday.order.service.response.ProductReadResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpMethod.PATCH;

@Slf4j
@Component
@RequiredArgsConstructor
public class CatalogClient {

    private RestClient restClient;

    @Value("${client.catalog-service.url}")
    private String catalogUrl;

    @PostConstruct
    public void init() {
        restClient = RestClient.create(catalogUrl);
    }

    public ProductReadResponse getProduct(Long productId) {
        return restClient.get()
                .uri("/api/v1/catalog/products/{productId}", productId)
                .retrieve()
                .body(ProductReadResponse.class);
    }

    public void decreaseStockCount(Long productId, Long count) {
        restClient.method(PATCH)
                .uri("/api/v1/catalog/products/{productId}/stock-count?count={count}", productId, count)
                .retrieve()
                .toBodilessEntity();
    }
}
