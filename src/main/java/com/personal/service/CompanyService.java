package com.personal.service;

import com.personal.pojo.web.Company;

import java.util.HashMap;
import java.util.List;

/**
 * @author 李箎
 */
public interface CompanyService {

    /**
     * 公司列表
     *
     * @param queryCondition 查询条件 该参数暂不使用
     * @return
     */
    List<Company> companyList(HashMap<String, Object> queryCondition);
}
