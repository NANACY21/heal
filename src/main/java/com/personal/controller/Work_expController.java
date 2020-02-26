package com.personal.controller;
import com.personal.pojo.Work_exp;
import com.personal.service.Work_expService;
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
public class Work_expController {
    @Autowired
    private Work_expService service;
    /**
     * 新增/编辑工作经历
     * @param work_exp
     * @return
     */
    @RequestMapping("/work_exp")
    @ResponseBody
    public String saveWork_exp(@RequestBody Work_exp work_exp) {
        System.out.println(work_exp);
        return service.saveWork_exp(work_exp);
    }

    /**
     * 用户简历的工作经历列表
     *
     * @param resumeId
     * @return
     */
    @RequestMapping("/getWork_expListByResumeId")
    @ResponseBody
    public List<Work_exp> getWork_expListByResumeId(@RequestBody long resumeId) {
        return service.getWork_expListByResumeId(resumeId);
    }
}
