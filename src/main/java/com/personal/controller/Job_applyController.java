package com.personal.controller;

import com.personal.pojo.Job_apply;
import com.personal.pojo.Position;
import com.personal.service.Job_applyService;
import com.personal.util.ConstPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 李箎
 */
@Controller
public class Job_applyController {
    @Autowired
    private Job_applyService service;

    /**
     * 申请职位，投递简历
     * 投递职位：MySQL insert一条，后置通知：redis新增一条
     * @param job_apply
     * @return
     */
    @RequestMapping("/postResume")
    @ResponseBody
    public String postResume(@RequestBody Job_apply job_apply) {
        System.out.println(job_apply);
        return service.postResume(job_apply);
    }

    /**
     * 投递箱 申请的职位列表
     * 读redis里的职位列表
     * @param userId
     * @return
     */
    @RequestMapping("/postList")
    @ResponseBody
    public List<Position> postList(@RequestBody long userId) {
        return service.postList(userId);
    }

    /**移除投递记录
     * 移除投递箱中的一条或多条 移除redis里的那条
     * @param id 投递箱列表项id
     * @return
     */
    public String removePost(@RequestBody long id) {
        return "";
    }
}
