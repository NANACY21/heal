package com.personal.mapper;

import com.personal.pojo.Job_apply;
import com.personal.pojo.Position;

import java.util.List;

public interface Job_applyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Job_apply record);

    int insertSelective(Job_apply record);

    Job_apply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Job_apply record);

    int updateByPrimaryKey(Job_apply record);

    /**
     * 申请的职位列表
     * @param userId
     * @return
     */
    List<Position> postList(long userId);

    /**
     * 该用户是否投递过该职位
     * @param job_apply
     * @return
     */
    Job_apply isPosted(Job_apply job_apply);
}