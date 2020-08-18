package com.personal.service;

import com.personal.pojo.Project_exp;

import java.util.List;

public interface Project_expService {
    /**
     * 新增/编辑一个项目经历
     *
     * @param project_exp 一个项目经历
     * @return
     */
    String saveProject_exp(Project_exp project_exp);

    /**
     * 删除一个项目经历
     * @param projectExpId 项目经历id
     * @return
     */
    String delProject_exp(long projectExpId);

    /**
     * 获得项目经历列表通过简历id
     * @param resumeId 简历id
     * @return
     */
    List<Project_exp> getProject_expListByResumeId(long resumeId);
}
