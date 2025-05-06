package cwchoiit.blackfriday.search.controller;

import cwchoiit.blackfriday.response.ApiResponse;
import cwchoiit.blackfriday.search.service.SearchService;
import cwchoiit.blackfriday.search.service.request.CreateTagToProductCacheRequest;
import cwchoiit.blackfriday.search.service.request.DeleteTagToProductCacheRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService searchService;

    @PostMapping("/cache")
    public ResponseEntity<ApiResponse<Void>> addTagCache(@RequestBody CreateTagToProductCacheRequest request) {
        searchService.addTagCache(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/cache")
    public ResponseEntity<ApiResponse<Void>> removeTagCache(@RequestBody DeleteTagToProductCacheRequest request) {
        searchService.removeTagCache(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tags/{tag}")
    public ResponseEntity<ApiResponse<List<Long>>> getTagProductIds(@PathVariable("tag") String tag) {
        return ResponseEntity.ok(ApiResponse.ok(searchService.getProductIdsByTag(tag)));
    }
}
