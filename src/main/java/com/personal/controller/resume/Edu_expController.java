package com.personal.controller.resume;

import com.personal.pojo.Edu_exp;
import com.personal.service.Edu_expService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 简历 - 教育经历
 *
 * @author 李箎
 */
@Controller
public class Edu_expController {

    @Autowired
    private Edu_expService service;

    /**
     * 添加/编辑一个教育经历
     *
     * @param edu_exp 一个教育经历
     * @return
     */
    @RequestMapping("/edu_exp")
    @ResponseBody
    public String saveEdu_exp(@RequestBody Edu_exp edu_exp) {
        System.out.println(edu_exp);
        return service.saveEdu_exp(edu_exp);
    }

    /**
     * 删除一个教育经历
     *
     * @param eduExpId 教育经历id
     * @return
     */
    @RequestMapping("/delEdu_exp")
    @ResponseBody
    public String delEdu_exp(@RequestBody long eduExpId) {
        return service.delEdu_exp(eduExpId);
    }

    /**
     * 获得教育经历列表通过简历id
     *
     * @param resumeId 简历id
     * @return
     */
    @RequestMapping("/getEdu_expListByResumeId")
    @ResponseBody
    public List<Edu_exp> getEdu_expListByResumeId(@RequestBody long resumeId) {
        return service.getEdu_expListByResumeId(resumeId);
    }
}
