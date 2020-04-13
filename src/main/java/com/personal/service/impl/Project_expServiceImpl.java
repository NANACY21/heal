package com.personal.service.impl;

import com.personal.mapper.Project_expMapper;
import com.personal.pojo.Project_exp;
import com.personal.service.Project_expService;
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
public class Project_expServiceImpl implements Project_expService {
    @Autowired
    private Project_expMapper mapper;

    /**
     * 添加/修改项目经历
     *
     * @param project_exp
     * @return
     */
    @Override
    public String saveProject_exp(Project_exp project_exp) {
        if (project_exp.getId() == null) {
            int i = mapper.insertSelective(project_exp);
            if (i > 0) {
                return "成功";
            }
        } else {
            int i = mapper.updateByPrimaryKeySelective(project_exp);
            if (i > 0) {
                return "成功";
            }
        }
        return "失败";
    }

    /**
     * 删除一个项目经历
     *
     * @param projectExpId 项目经历id
     * @return
     */
    @Override
    public String delProject_exp(long projectExpId) {
        int i = mapper.deleteByPrimaryKey(projectExpId);
        String temp = "删除项目经历";
        if (i > 0) {
            temp += "成功";
        } else {
            temp += "失败";
        }
        return temp;
    }

    /**
     * 某个简历的项目经历列表
     *
     * @param resumeId
     * @return
     */
    @Override
    public List<Project_exp> getProject_expListByResumeId(long resumeId) {
        return mapper.getProject_expListByResumeId(resumeId);
    }
}
