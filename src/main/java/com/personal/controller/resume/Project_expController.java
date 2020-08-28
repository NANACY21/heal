package com.personal.controller.resume;

import com.personal.pojo.Project_exp;
import com.personal.service.Project_expService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 简历 - 项目经历
 *
 * @author 李箎
 */
@Controller
public class Project_expController {

    @Autowired
    private Project_expService service;

    /**
     * 新增/编辑一个项目经历
     *
     * @param project_exp 一个项目经历
     * @return
     */
    @RequestMapping("/project_exp")
    @ResponseBody
    public String saveProject_exp(@RequestBody Project_exp project_exp) {
        System.out.println(project_exp);
        return service.saveProject_exp(project_exp);
    }

    /**
     * 删除一个项目经历
     *
     * @param projectExpId 项目经历id
     * @return
     */
    @RequestMapping("/delProject_exp")
    @ResponseBody
    public String delProject_exp(@RequestBody long projectExpId) {
        return service.delProject_exp(projectExpId);
    }

    /**
     * 获得项目经历列表通过简历id
     *
     * @param resumeId 简历id
     * @return
     */
    @RequestMapping("/getProject_expListByResumeId")
    @ResponseBody
    public List<Project_exp> getProject_expListByResumeId(@RequestBody long resumeId) {
        return service.getProject_expListByResumeId(resumeId);
    }
}
