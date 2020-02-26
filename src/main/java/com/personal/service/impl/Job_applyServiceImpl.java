package com.personal.service.impl;

import com.personal.mapper.Job_applyMapper;
import com.personal.pojo.Job_apply;
import com.personal.pojo.Position;
import com.personal.service.Job_applyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
     * 申请职位，投递简历
     *
     * @param job_apply
     * @return
     */
    @Override
    public String postResume(Job_apply job_apply) {
        Job_apply posted = null;
        posted = mapper.isPosted(job_apply);
        if (posted != null) {
            return "你已投递过该职位，不可重复投递";
        }
        int i = mapper.insertSelective(job_apply);
        if (i > 0) {
            return "成功";
        }
        return "失败";
    }

    /**
     * 投递箱 申请的职位列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<Position> postList(long userId) {
        return mapper.postList(userId);
    }
}
