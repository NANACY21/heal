package com.personal.controller.resume;
import com.personal.pojo.Work_exp;
import com.personal.service.Work_expService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 工作经历
 *
 * @author 李箎
 */
@Controller
public class Work_expController {

    @Autowired
    private Work_expService service;

    /**
     * 新增/编辑一个工作经历
     *
     * @param work_exp 一个工作经历
     * @return
     */
    @RequestMapping("/work_exp")
    @ResponseBody
    public String saveWork_exp(@RequestBody Work_exp work_exp) {
        System.out.println(work_exp);
        return service.saveWork_exp(work_exp);
    }

    /**
     * 删除一个工作经历
     *
     * @param workExpId 工作经历id
     * @return
     */
    @RequestMapping("/delWork_exp")
    @ResponseBody
    public String delWork_exp(@RequestBody long workExpId) {
        return service.delWork_exp(workExpId);
    }

    /**
     * 获得简历的工作经历列表通过简历id
     *
     * @param resumeId 简历id
     * @return
     */
    @RequestMapping("/getWork_expListByResumeId")
    @ResponseBody
    public List<Work_exp> getWork_expListByResumeId(@RequestBody long resumeId) {
        return service.getWork_expListByResumeId(resumeId);
    }
}
