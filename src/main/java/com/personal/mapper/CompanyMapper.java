package com.personal.mapper;
import com.personal.pojo.web.Company;

import java.util.List;

/**
 * @author 李箎
 */
public interface CompanyMapper {

    /**
     * 获取公司卡片列表
     * @return
     */
    List<Company> companyList();
}