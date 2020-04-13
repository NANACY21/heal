package com.personal.mapper;
import com.personal.pojo.Project_exp;

import java.util.List;

/**
 * 项目经历
 */
public interface Project_expMapper {
    /**
     * 删除一个项目经历
     * @param id 项目经历id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    int insert(Project_exp record);

    int insertSelective(Project_exp record);

    Project_exp selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Project_exp record);

    int updateByPrimaryKeyWithBLOBs(Project_exp record);

    int updateByPrimaryKey(Project_exp record);

    /**
     * 简历的项目经历列表
     *
     * @param resumeId
     * @return
     */
    List<Project_exp> getProject_expListByResumeId(Long resumeId);
}