package cwchoiit.blackfriday.order.service.response;

import java.util.List;

public record ProductReadResponse(Long productId,
                                  Long sellerId,
                                  String name,
                                  String description,
                                  Long price,
                                  Long stockCount,
                                  List<String> tags) {

}
