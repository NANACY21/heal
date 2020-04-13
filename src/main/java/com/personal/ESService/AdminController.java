package com.personal.ESService;

import com.personal.util.ConstPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 李箎
 */
@Controller
public class AdminController {
    @Autowired
    private ElasticSearchUtil searchUtil;

    @RequestMapping("/createIndex")
    @ResponseBody
    public String createIndex() {
        return searchUtil.createIndex(ConstPool.INDEX_NAME);
    }

    @RequestMapping("/delIndex")
    @ResponseBody
    public String delIndex() {
        return searchUtil.delIndex(ConstPool.INDEX_NAME);
    }
}
