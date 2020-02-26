package com.personal.redisService.tool;

import com.personal.redisService.RedisConnection;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @author 李箎
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisConnection con;

    /**
     * 获取Redis所有key
     * @return
     */
    public Set<String> getKeyList() {
        return RedisConnection.getJedis().keys("*");
    }

    /**
     * 获取指定key
     * @param key
     * @return
     */
    public Set<String> getKeyList(String key) {
        return RedisConnection.getJedis().keys(key);
    }

    /**
     * 移除指定的一个或多个key key不存在则忽略
     * @param key
     */
    public void delKey(String... key) {
        Long del = RedisConnection.getJedis().del(key);
    }
}
