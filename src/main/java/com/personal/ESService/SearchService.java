package com.personal.ESService;

import com.personal.redisService.tool.RedisUtil;
import com.personal.util.ConstPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 搜索服务
 *
 * @author 李箎
 */
@SuppressWarnings("ALL")
@Service
@Transactional
public class SearchService {
    @Autowired
    private RedisUtil redisUtil;
    /**
     * 全局搜索
     * @param map 用户名、搜索的内容
     * @return
     */
    public String globalSearch(Map<String,Object> map) {
        return "已提交";
    }

    /**
     * 获取全局站内搜索历史记录
     * @param username
     * @return
     */
    public List<String> getSearchHistory(String username) {
        return redisUtil.getListVal(username + ConstPool.SEARCH_HISTORY);
    }

    /**
     * 清空全局站内搜索历史记录
     * @param username
     * @return
     */
    public String delSearchHistory(String username) {
        redisUtil.delKey(username + ConstPool.SEARCH_HISTORY);
        return "删除成功";
    }
}
