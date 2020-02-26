package com.personal.service;

import com.personal.pojo.Job_apply;
import com.personal.pojo.Position;

import java.util.List;

/**
 * @author 李箎
 */
public interface Job_applyService {
    /**
     * 申请职位，投递简历
     * @param job_apply
     * @return
     */
    String postResume(Job_apply job_apply);

    /**
     * 投递箱 申请的职位列表
     * @param userId
     * @return
     */
    List<Position> postList(long userId);
}
