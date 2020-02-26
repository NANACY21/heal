package com.personal.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.personal.mapper.CompanyMapper;
import com.personal.pojo.web.Company;
import com.personal.service.CompanyService;
import com.personal.util.ConstPool;
import com.personal.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("ALL")
@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    CompanyMapper mapper;

    /**
     * 公司列表
     *
     * @param queryCondition 查询条件
     * @return
     */
    @Override
    public List<Company> companyList(HashMap<String, Object> queryCondition) {
        //拿到了id，在招职位数量
        List<Company> companyList = mapper.companyList();
        for (Company company : companyList) {
            String jsonStr = Util.readJsonFileTool(new File(ConstPool.JSON_PATH, company.getId() + ".json"));
            //json串->对象
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            String companyName = jsonObject.get("name").toString();
            //设置公司名称
            company.setName(companyName);
            JSONObject introduction = jsonObject.getJSONObject("introduction");
            String summary = introduction.get("summary").toString();
            company.setIntroduction(summary);
        }
        return companyList;
    }
}
