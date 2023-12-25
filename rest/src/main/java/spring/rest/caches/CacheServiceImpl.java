package spring.rest.caches;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CacheServiceImpl<T> implements CacheService<T> {
    private final ObjectMapper objectMapper;

    @Autowired
    public CacheServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public CacheData createNewCacheData(String cacheKey, T t) throws JsonProcessingException {
        return new CacheData(cacheKey, objectMapper.writeValueAsString(t));
    }

    @Override
    public List<T> readCachedData(String cachedDataAsString) {
        try {
            return objectMapper.readValue(cachedDataAsString, new TypeReference<List<T>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void editCachedData(CacheData cacheData, Object o) {
        try {
            cacheData.setValue(objectMapper.writeValueAsString(o));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Object o) {

    }
}