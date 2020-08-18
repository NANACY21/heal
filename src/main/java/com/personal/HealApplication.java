package com.personal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * maven reimport慎重，因为很慢！
 * 生成字符画：
 * http://www.network-science.de/ascii/
 * IDEA激活码：
 * http://idea.medeming.com/jets/
 * @author 李箎
 */
@SpringBootApplication
@EnableRedisHttpSession
//在线人数
@ServletComponentScan
@MapperScan("com.personal.mapper")
public class HealApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(HealApplication.class);
        application.setBannerMode(Banner.Mode.CONSOLE);
        SpringApplication.run(HealApplication.class, args);
    }
}
