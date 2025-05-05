package cwchoiit.blackfriday.catalog.service.request;

import java.util.List;

public record CreateProductRequest(Long sellerId,
                                   String name,
                                   String description,
                                   Long price,
                                   Long stockCount,
                                   List<String> tags) {
}
