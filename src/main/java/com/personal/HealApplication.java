package com.personal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot项目
 * maven reimport慎重，因为很慢！！！
 */
@SpringBootApplication
@MapperScan("com.personal.mapper")
public class HealApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealApplication.class, args);
    }

}
