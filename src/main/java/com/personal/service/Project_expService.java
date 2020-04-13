package com.personal.service;

import com.personal.pojo.Project_exp;

import java.util.List;

public interface Project_expService {
    /**
     * 添加/修改项目经历
     *
     * @param project_exp
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
     * 某个简历的项目经历列表
     * @param resumeId
     * @return
     */
    List<Project_exp> getProject_expListByResumeId(long resumeId);
}
