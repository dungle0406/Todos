package spring.rest.caches;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@AllArgsConstructor
@Getter
@RedisHash("cacheData")
public class CacheData {
    @Id
    private String key;

    @Indexed
    private String value;
}