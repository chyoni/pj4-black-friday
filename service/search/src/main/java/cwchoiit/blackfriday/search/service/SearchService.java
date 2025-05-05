package cwchoiit.blackfriday.search.service;

import cwchoiit.blackfriday.search.service.request.CreateTagToProductCacheRequest;
import cwchoiit.blackfriday.search.service.request.DeleteTagToProductCacheRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final StringRedisTemplate redisTemplate;

    public void addTagCache(CreateTagToProductCacheRequest request) {
        request.tags().forEach(tag ->
                redisTemplate.opsForSet()
                        .add(tag, String.valueOf(request.productId()))
        );
    }

    public void removeTagCache(DeleteTagToProductCacheRequest request) {
        request.tags().forEach(tag ->
                redisTemplate.opsForSet()
                        .remove(tag, String.valueOf(request.productId()))
        );
    }

    public List<Long> getProductIdsByTag(String tag) {
        return Optional.ofNullable(redisTemplate.opsForSet().members(tag))
                .orElseGet(Collections::emptySet)
                .stream()
                .map(Long::valueOf)
                .toList();
    }
}
