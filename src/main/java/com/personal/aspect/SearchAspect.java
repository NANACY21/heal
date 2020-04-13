package com.personal.aspect;

import com.personal.redisService.tool.RedisUtil;
import com.personal.util.ConstPool;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author 李箎
 */
@SuppressWarnings("ALL")
@Aspect
@Component
public class SearchAspect {
    @Autowired
    RedisUtil redisUtil;
    @Before("execution(public * com.personal.ESService.SearchService.globalSearch(..))")
    public void beforeGlobalSearch(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Map<String, Object> map = (Map<String, Object>) args[0];
        if (map.size() == 1) {
            //未登录的用户不记载历史记录
            return;
        }
        //用户
        String username = map.get("username").toString();
        //搜索的内容
        String searchContent = map.get("searchContent").toString();
        redisUtil.insert(username + ConstPool.SEARCH_HISTORY, searchContent);
    }
}
