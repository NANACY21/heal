package com.personal.service.impl;

import com.personal.mapper.Job_applyMapper;
import com.personal.pojo.Job_apply;
import com.personal.pojo.Position;
import com.personal.service.Job_applyService;
import com.personal.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李箎
 */
@SuppressWarnings("ALL")
@Service
@Transactional
public class Job_applyServiceImpl implements Job_applyService {
    @Autowired
    private Job_applyMapper mapper;

    /**
     * 求职者申请职位，投递简历
     * 投递职位：MySQL insert一条
     *
     * @param job_apply
     * @return
     */
    @Override
    public String postResume(Job_apply job_apply) {
        Job_apply posted = null;
        posted = mapper.isPosted(job_apply);
        if (posted != null) {
            job_apply.setId(null);
            job_apply.setPositionId(null);
            job_apply.setUserId(null);
            return "你已投递过该职位，不可重复投递";
        }
        job_apply.setStatus(0);
        int i = mapper.insertSelective(job_apply);
        if (i > 0) {
            return "成功";
        }
        return "失败";
    }

    /**
     * 某求职者的投递箱（申请的职位列表）
     *
     * @param map userId，当前页，页大小，投递状态
     * @return
     */
    @Override
    public List<Position> postList(Map<String, Object> map) {
        Map<String, Object> temp = new HashMap<>();
        long userId = Long.parseLong(map.get("id").toString());
        int postStatus = Integer.parseInt(map.get("postStatus").toString());
        temp.put("userId", userId);
        temp.put("postStatus", postStatus);
        if (map.get("currentPage") != null && map.get("pageSize") != null) {
            int currentPage = Integer.parseInt(map.get("currentPage").toString());
            int pageSize = Integer.parseInt(map.get("pageSize").toString());
            temp.put("beginRowIndex", Util.getLimitVariable(currentPage, pageSize).get("beginRowIndex"));
            temp.put("rowNum", Util.getLimitVariable(currentPage, pageSize).get("rowNum"));
        }
        if (postStatus >= 0) {
            return mapper.postList(temp);
        } else {
            return mapper.postList2(temp);
        }
    }

    /**
     * 某公司所有有投递的职位列表
     *
     * @param map companyId，当前页，页大小
     * @return
     */
    @Override
    public List<Position> hasPostList(Map<String, Object> map) {
        if (map.get("currentPage") != null && map.get("pageSize") != null) {
            int currentPage = Integer.parseInt(map.get("currentPage").toString());
            int pageSize = Integer.parseInt(map.get("pageSize").toString());
            map.put("beginRowIndex", Util.getLimitVariable(currentPage, pageSize).get("beginRowIndex"));
            map.put("rowNum", Util.getLimitVariable(currentPage, pageSize).get("rowNum"));
        }
        return mapper.hasPostList(map);
    }
}
