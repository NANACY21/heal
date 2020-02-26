package com.personal.mapper;

import com.personal.pojo.Project_exp;
import com.personal.pojo.Work_exp;

import java.util.List;

public interface Work_expMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Work_exp record);

    int insertSelective(Work_exp record);

    Work_exp selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Work_exp record);

    int updateByPrimaryKeyWithBLOBs(Work_exp record);

    int updateByPrimaryKey(Work_exp record);

    /**
     * 简历的工作经历列表
     * @param resumeId
     * @return
     */
    List<Work_exp> getWork_expListByResumeId(Long resumeId);
}