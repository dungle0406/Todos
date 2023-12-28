package spring.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.lang.reflect.Method;

@SpringBootApplication
public class RestApplication {
    @Bean("keyGenerator")
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(o.getClass().getName());
                sb.append(method.getName());
                for (Object param : params) {
                    sb.append(param.toString());
                }
                return sb.toString();
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

}
