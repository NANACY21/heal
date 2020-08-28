package com.personal.controller.company;

import com.personal.pojo.web.Company;
import com.personal.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * 公司
 *
 * @author 李箎
 */
@Controller
public class CompanyController {

    @Autowired
    private CompanyService service;

    /**
     * 获取公司卡片列表
     *
     * @param queryCondition 查询条件 该参数暂不使用
     * @return
     */
    @RequestMapping("/companyList")
    @ResponseBody
    public List<Company> companyList(@RequestBody HashMap<String, Object> queryCondition) {
        return service.companyList(queryCondition);
    }
}
