package com.personal.mapper;

import com.personal.pojo.Edu_exp;

import java.util.List;

public interface Edu_expMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Edu_exp record);

    int insertSelective(Edu_exp record);

    Edu_exp selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Edu_exp record);

    int updateByPrimaryKey(Edu_exp record);

    /**
     * 简历的教育经历列表
     * @param resumeId
     * @return
     */
    List<Edu_exp> getEdu_expListByResumeId(Long resumeId);
}