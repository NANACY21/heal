package com.personal.aspect;

import com.personal.redisService.tool.RedisUtil;
import com.personal.util.ConstPool;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 全局站内搜索 要保存搜索历史记录
     * @param joinPoint
     */
    @Before("execution(public * com.personal.ESService.SearchService.globalSearch(..))")
    public void beforeGlobalSearch(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        //目标方法第一个参数
        Map<String, Object> map = (Map<String, Object>) args[0];
        //未登录的用户不记录搜索历史记录
        if (map.size() == 1) {
            return;
        }
        //用户名
        String username = map.get("username").toString();
        //搜索内容
        String searchContent = map.get("searchContent").toString();
        //搜索记录保存到Redis
        redisUtil.insert(username + ConstPool.SEARCH_HISTORY, searchContent);
    }
}
