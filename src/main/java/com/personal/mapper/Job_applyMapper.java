package com.personal.mapper;

import com.personal.pojo.Job_apply;
import com.personal.pojo.Position;

import java.util.List;
import java.util.Map;

public interface Job_applyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Job_apply record);

    int insertSelective(Job_apply record);

    Job_apply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Job_apply record);

    int updateByPrimaryKey(Job_apply record);

    /**
     * 申请的职位列表 投递箱
     *
     * @param map userId，beginRowIndex，页大小
     * @return
     */
    List<Position> postList(Map<String, Object> map);
    List<Position> postList2(Map<String, Object> map);

    /**
     * 某公司所有有投递的职位的列表
     *
     * @param map companyId 起始行索引 页大小
     * @return
     */
    List<Position> hasPostList(Map<String, Object> map);

    /**
     * 该用户是否投递过该职位
     * @param job_apply
     * @return
     */
    Job_apply isPosted(Job_apply job_apply);
}