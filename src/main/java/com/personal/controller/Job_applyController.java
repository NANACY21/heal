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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 职位申请
 *
 * @author 李箎
 */
@Controller
public class Job_applyController {

    @Autowired
    private Job_applyService service;

    /**
     * 求职者申请职位，投递简历
     *
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
     * 某求职者的投递箱（申请的职位列表）
     *
     * @param map userId，当前页，页大小，投递状态
     * @return
     */
    @RequestMapping("/postList")
    @ResponseBody
    public List<Position> postList(@RequestBody Map<String, Object> map) {
        return service.postList(map);
    }

    /**
     * 某求职者的投递箱（申请的职位列表）长度
     *
     * @param map userId，当前页，页大小，投递状态
     * @return
     */
    @RequestMapping("/postListLength")
    @ResponseBody
    public int postListLength(@RequestBody Map<String, Object> map) {
        return service.postList(map).size();
    }

    /**
     * 某公司所有有投递的职位列表
     *
     * @param map companyId，当前页，页大小
     * @return
     */
    @RequestMapping("/hasPostList")
    @ResponseBody
    public List<Position> hasPostList(@RequestBody Map<String, Object> map) {
        return service.hasPostList(map);
    }

    /**
     * 某公司所有有投递的职位列表长度
     *
     * @param map companyId，当前页，页大小
     * @return
     */
    @RequestMapping("/hasPostListLength")
    @ResponseBody
    public int hasPostListLength(@RequestBody Map<String, Object> map) {
        return service.hasPostList(map).size();
    }

    /**
     * 移除投递箱中的一条或多条记录
     *
     * @param id 投递箱列表项id
     * @return
     */
    public String removePost(@RequestBody long id) {
        return "";
    }
}
