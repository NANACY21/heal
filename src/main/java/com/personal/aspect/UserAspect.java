package com.personal.aspect;

import com.personal.mapper.UsersMapper;
import com.personal.pojo.Users;
import com.personal.redisService.RedisConnection;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * 账号信息
 *
 * @author 李箎
 */
@SuppressWarnings("ALL")
@Aspect
@Component
public class UserAspect {

    @Autowired
    UsersMapper mapper;
    /**
     * 控制器 修改用户名之前
     *
     * @param joinPoint
     */
    @Before("execution(public * com.personal.controller.UsersController.updateUsername(..))")
    public void beforeUpdateUsername(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        //用户新的信息
        Users users = (Users) args[0];
        //该用户原来的信息
        Users users1 = mapper.selectByPrimaryKey(users.getId());
        //更新Redis
        Jedis jedis = RedisConnection.getJedis();
        jedis.del(users1.getUsername());
        jedis.set(users.getUsername(), users1.getPassword());
        users.setCompanyId(null);
        users.setPassword(null);
        users.setUserType(null);
        users.setUserId(null);
        users.setEmail(null);
    }

    /**
     * 控制器 修改密码之后
     * @param joinPoint
     */
    @After("execution(public * com.personal.controller.UsersController.updatePassword(..))")
    public void afterUpdatePassword(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        //用户新的信息
        Users users = (Users) args[0];
        String newPassword = users.getNewPassword();
        if (newPassword != null) {
            //更新Redis
            Jedis jedis = RedisConnection.getJedis();
            jedis.set(users.getUsername(), newPassword);
        }
    }
}
