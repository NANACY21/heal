package com.personal.aspect;

import com.personal.mapper.UsersMapper;
import com.personal.pojo.Users;
import com.personal.redisService.RedisConnection;
import com.personal.redisService.tool.RedisUtil;
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
    @Autowired
    RedisUtil redisUtil;

    /**
     * 控制器 修改用户名之前
     *
     * @param joinPoint
     */
    @Before("execution(public * com.personal.controller.UsersController.updateUsername(..))")
    public void beforeUpdateUsername(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        //用户新的信息
        Users newUser = (Users) args[0];
        //该用户原来的信息
        Users oldUser = mapper.selectByPrimaryKey(newUser.getId());
        //更新Redis
        redisUtil.delKey(oldUser.getUsername());
        redisUtil.set(newUser.getUsername(), oldUser.getPassword());
        newUser.setCompanyId(null);
        newUser.setPassword(null);
        newUser.setUserType(null);
        newUser.setUserId(null);
        newUser.setEmail(null);
    }

    /**
     * 控制器 修改密码之后
     * @param joinPoint
     */
    @After("execution(public * com.personal.controller.UsersController.updatePassword(..))")
    public void afterUpdatePassword(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        //用户新的信息
        Users newUser = (Users) args[0];
        //用户新的密码
        String newPassword = newUser.getNewPassword();
        if (newPassword != null) {
            //更新Redis
            //目标用户
            Users targetUser = mapper.selectByEmail(newUser.getEmail());
            if (targetUser == null) {
                return;
            }
            redisUtil.set(targetUser.getUsername(), newPassword);
        }
    }
}
