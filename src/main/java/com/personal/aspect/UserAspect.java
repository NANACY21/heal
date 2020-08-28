package com.personal.aspect;

import com.personal.mapper.UsersMapper;
import com.personal.pojo.Users;
import com.personal.redisService.RedisConnection;
import com.personal.redisService.tool.RedisUtil;
import com.personal.util.Util;
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
        //前端提交的数据 包含要改成的username
        Users user_web = (Users) args[0];
        //更新Redis
        //该用户原来的信息
        Users oldUser = mapper.selectByPrimaryKey(user_web.getId());
        redisUtil.delKey(oldUser.getUsername());
    }

    /**更新Redis
     * 控制器 修改密码之后
     * @param joinPoint
     */
    @After("execution(public * com.personal.controller.UsersController.updatePassword(..))")
    public void afterUpdatePassword(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        //前端提交的数据 包含要改成的password
        Users user_web = (Users) args[0];
        //用户新的密码
        String newPassword = user_web.getNewPassword();
        if (newPassword != null) {
            //目标用户
            Users targetUser = mapper.selectByEmail(user_web.getEmail());
            //更新信息
            targetUser.setPassword(newPassword);
            if (targetUser == null) {
                return;
            }
            redisUtil.set(targetUser.getUsername(), Util.objectToJson(targetUser));
        }
    }
}
