package com.personal.controller;

import com.personal.pojo.Edu_exp;
import com.personal.service.Edu_expService;
import com.personal.util.ConstPool;
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
public class Edu_expController {
    @Autowired
    private Edu_expService service;

    /**
     * 添加/编辑教育经历
     *
     * @param edu_exp
     * @return
     */
    @RequestMapping("/edu_exp")
    @ResponseBody
    public String saveEdu_exp(@RequestBody Edu_exp edu_exp) {
        System.out.println(edu_exp);
        return service.saveEdu_exp(edu_exp);
    }

    /**
     * @param resumeId
     * @return
     */
    @RequestMapping("/getEdu_expListByResumeId")
    @ResponseBody
    public List<Edu_exp> getEdu_expListByResumeId(@RequestBody long resumeId) {
        return service.getEdu_expListByResumeId(resumeId);
    }
}
