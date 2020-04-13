package com.personal.redisService.tool;

import com.personal.redisService.RedisConnection;
import com.personal.util.ConstPool;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**https://blog.csdn.net/qq_38567039/article/details/89488380
 * Java对象存进Redis https://blog.csdn.net/kunchengyue/article/details/83246902
 * @author 李箎
 */
@Component
public class RedisUtil {
    /**
     * 获取所有key
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

    /**
     * 获取指定key的值
     * @param key
     * @return
     */
    public Object get(String key) {
        return RedisConnection.getJedis().get(key);
    }

    /**
     * 获取指定列表中的值 前20条
     * @param key
     * @return
     */
    public List<String> getListVal(String key) {
        return RedisConnection.getJedis().lrange(key, 0, 20);
    }


    /**
     * 向列表添加数据
     * @param listName 列表名 键
     * @param value 值
     */
    public void insert(String listName, String value) {
        RedisConnection.getJedis().lpush(listName, value);
    }

    /**
     * 移除某列表中某个元素
     * @param listName 列表名 键
     * @param value 值
     */
    public void delete(String listName, String value) {
        RedisConnection.getJedis().lrem(listName, 1, value);
    }

    /**
     * 已登录在线人数
     *
     * @return
     */
    public int onlineCount() {
        int count = 0;
        Set<String> keys = RedisConnection.getJedis().keys("spring:session:sessions:" + "*");
        if (keys != null && keys.size() > 0) {
            count = keys.size() / 2;
        } else {
            count = 0;
        }
        return count;
    }
}
