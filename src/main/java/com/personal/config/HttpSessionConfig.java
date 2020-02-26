package com.personal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;

/**
 * @author 李箎
 */
@Configuration
public class HttpSessionConfig {

    /**
     * 解决redis集群环境没有开启Keyspace notifications导致的启动SpringBoot报错：
     * Error creating bean with name 'enableRedisKeyspaceNotificationsInitializer' defined in class path resource
     * @return
     */
    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }
}
