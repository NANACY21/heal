package com.personal.controller.search;

import com.personal.ESService.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author 李箎
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService service;

    /**
     * 全局搜索 站内搜索
     *
     * @param temp 搜索内容
     * @return
     */
    @RequestMapping("/globalSearch")
    @ResponseBody
    public String globalSearch(@RequestBody Map<String, Object> temp) {
        return service.globalSearch(temp);
    }

    /**
     * 获取全局站内搜索历史记录
     * @param username 用户名
     * @return
     */
    @RequestMapping("/getSearchHistory")
    @ResponseBody
    public List<String> getSearchHistory(@RequestBody String username) {
        return service.getSearchHistory(username);
    }

    /**
     * 清空全局搜索历史记录
     * @param username
     * @return
     */
    @RequestMapping("/delSearchHistory")
    @ResponseBody
    public String delSearchHistory(@RequestBody String username) {
        return service.delSearchHistory(username);
    }
}
