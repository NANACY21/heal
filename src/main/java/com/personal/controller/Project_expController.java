package com.personal.controller;

import com.personal.pojo.Project_exp;
import com.personal.pojo.Resume;
import com.personal.service.Project_expService;
import com.personal.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 李箎
 */
@Controller
public class Project_expController {
    @Autowired
    private Project_expService service;
    /**
     * 新增/编辑项目经历
     * @param project_exp
     * @return
     */
    @RequestMapping("/project_exp")
    @ResponseBody
    public String saveProject_exp(@RequestBody Project_exp project_exp) {
        System.out.println("简历基本信息");
        System.out.println(project_exp);
        return service.saveProject_exp(project_exp);
    }

    /**
     * 用户简历的项目经历列表
     *
     * @param resumeId
     * @return
     */
    @RequestMapping("/getProject_expListByResumeId")
    @ResponseBody
    public List<Project_exp> getProject_expListByResumeId(@RequestBody long resumeId) {
        return service.getProject_expListByResumeId(resumeId);
    }
}
