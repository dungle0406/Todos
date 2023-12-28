package spring.rest.caches;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import spring.rest.todos.TodoDTO;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CacheKeyGenerator implements KeyGenerator {
    private static final Logger log = LoggerFactory.getLogger(CacheKeyGenerator.class);

    @Override
    public Object generate(Object target, Method method, Object... params) {
        Object[] key = new Object[params.length + 2];
        key[0] = method.getDeclaringClass().getName();
        key[1] = method.getName();

        for (int i = 0; i < params.length; i++) {
            if (params[i] instanceof TodoDTO todoDTO) {
                key[i + 2] = todoDTO;
            } else {
                key[i + 2] = params[i];
            }
        }

        int hashCode = Arrays.deepHashCode(key);
        log.info("Generated Cache Key Hash Code: {}", hashCode);
        return hashCode;
    }
}

