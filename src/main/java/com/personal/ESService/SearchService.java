package com.personal.ESService;

import com.personal.pojo.web.ReleasePosition;
import com.personal.redisService.tool.RedisUtil;
import com.personal.util.ConstPool;
import com.personal.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Autowired
    private ElasticSearchUtil searchUtil;
    @Autowired
    private Util util;
    /**
     * 全局站内搜索
     *
     * @param map 用户名 搜索内容
     * @return
     */
    public List<ReleasePosition> globalSearch(Map<String, Object> map) {
        //搜索内容
        String searchContent = map.get("searchContent").toString();
        List<ReleasePosition> list = searchUtil.getReleasePositionList(searchContent);
        if (list == null || list.size() == 0) {
            return new ArrayList<>();
        }
        list = util.getCompanyInfo(list);
        list = util.getPublisherInfo(list);
        return list;
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
