package spring.rest.caches;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import spring.rest.todos.TodoService;

import java.lang.reflect.Method;

@Component
public class CacheKeyGenerator implements KeyGenerator {
    private static final Logger log = LoggerFactory.getLogger(CacheKeyGenerator.class);

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return target.getClass().getSimpleName() + "_"
                + method.getName() + "_"
                + StringUtils.arrayToDelimitedString(params, "_");
    }
}


