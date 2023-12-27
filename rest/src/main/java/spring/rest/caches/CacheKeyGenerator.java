package spring.rest.caches;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CacheKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(final Object target, final Method method, final Object... params) {
        final List<Object> key = new ArrayList<>();
        key.add(method.getDeclaringClass().getName());
        key.add(method.getName());

        Collections.addAll(key, params);
        return key;
    }
}
