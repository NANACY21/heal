package com.personal.ESService;

import com.personal.util.ConstPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 管理员
 *
 * @author 李箎
 */
@Controller
public class AdminController {

    @Autowired
    private ElasticSearchUtil searchUtil;

    /**
     * 创建索引
     * @return
     */
    @RequestMapping("/createIndex")
    @ResponseBody
    public String createIndex() {
        return searchUtil.createIndex(ConstPool.INDEX_NAME);
    }

    /**
     * 删除索引
     * @return
     */
    @RequestMapping("/delIndex")
    @ResponseBody
    public String delIndex() {
        return searchUtil.delIndex(ConstPool.INDEX_NAME);
    }
}
