package spring.rest.caches;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CacheService<T> {
    CacheData createNewCacheData(String cacheKey, T t) throws JsonProcessingException;

    List<T> readCachedData(String cachedDataAsString);

    void editCachedData(CacheData cacheData, T t);
}