package cwchoiit.blackfriday.search.service.request;

import java.util.List;

public record DeleteTagToProductCacheRequest(Long productId, List<String> tags) {
}
