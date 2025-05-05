package cwchoiit.blackfriday.search.service.request;

import java.util.List;

public record CreateTagToProductCacheRequest(Long productId, List<String> tags) {
}
