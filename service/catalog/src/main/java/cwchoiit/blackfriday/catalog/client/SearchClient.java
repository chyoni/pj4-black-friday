package cwchoiit.blackfriday.catalog.client;


import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchClient {

    private RestClient restClient;

    @Value("${client.search-service.url}")
    private String searchServiceUrl;

    @PostConstruct
    public void init() {
        restClient = RestClient.create(searchServiceUrl);
    }

    public void addTagCache(Long productId, List<String> tags) {
        restClient.method(POST)
                .uri("/api/v1/search/cache")
                .body(new TagToProductCacheRequest(productId, tags))
                .retrieve()
                .toBodilessEntity();
    }

    public void removeTagCache(Long productId, List<String> tags) {
        restClient.method(DELETE)
                .uri("/api/v1/search/cache")
                .body(new TagToProductCacheRequest(productId, tags))
                .retrieve()
                .toBodilessEntity();
    }



    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TagToProductCacheRequest {
        private Long productId;
        private List<String> tags;
    }
}
