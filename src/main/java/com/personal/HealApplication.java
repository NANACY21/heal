package com.personal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Spring Boot项目
 * maven reimport慎重，因为很慢！！！
 * @author 李箎
 */
@SpringBootApplication
@EnableRedisHttpSession
@MapperScan("com.personal.mapper")
public class HealApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealApplication.class, args);
    }

}
