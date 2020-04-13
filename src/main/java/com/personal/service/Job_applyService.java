package com.personal.service;

import com.personal.pojo.Job_apply;
import com.personal.pojo.Position;

import java.util.List;
import java.util.Map;

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
     *
     * @param map userId，当前页，页大小
     * @return
     */
    List<Position> postList(Map<String, Object> map);

    /**
     * 某公司所有有投递的职位的列表
     *
     * @param map companyId 当前页 页大小
     * @return
     */
    List<Position> hasPostList(Map<String, Object> map);
}
