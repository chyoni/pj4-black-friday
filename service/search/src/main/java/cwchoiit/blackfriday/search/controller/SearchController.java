package cwchoiit.blackfriday.search.controller;

import cwchoiit.blackfriday.search.service.SearchService;
import cwchoiit.blackfriday.search.service.request.CreateTagToProductCacheRequest;
import cwchoiit.blackfriday.search.service.request.DeleteTagToProductCacheRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService searchService;

    @PostMapping("/cache")
    public void addTagCache(@RequestBody CreateTagToProductCacheRequest request) {
        searchService.addTagCache(request);
    }

    @DeleteMapping("/cache")
    public void removeTagCache(@RequestBody DeleteTagToProductCacheRequest request) {
        searchService.removeTagCache(request);
    }

    @GetMapping("/tags/{tag}")
    public List<Long> getTagProductIds(@PathVariable("tag") String tag) {
        return searchService.getProductIdsByTag(tag);
    }
}
