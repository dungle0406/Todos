package spring.rest.caches;

import java.lang.reflect.Method;
import java.util.Arrays;

public class KeyGenerator implements org.springframework.cache.interceptor.KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        return Arrays.deepHashCode(params);
    }
}
