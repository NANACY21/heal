package com.personal.redisService;

import com.personal.util.ConstPool;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * @author 李箎
 * 参考：https://www.runoob.com/redis/redis-java.html
 */
@Component
public class RedisConnection {

    public static Jedis getJedis() {
        //连接本地Redis服务
        Jedis jedis = new Jedis(ConstPool.IP);
        //验证密码 无密码省略
        //jedis.auth("password");
        System.out.println("连接" + ConstPool.IP + "Redis成功");
        //连接
        jedis.connect();
        //断开连接
        //jedis.disconnect();
        System.out.println(ConstPool.IP + "Redis服务正在运行" + jedis.ping());
        return jedis;
    }
}
