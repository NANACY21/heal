package com.personal.controller.resume;

import com.personal.pojo.Resume;
import com.personal.pojo.web.ResumeOutline;
import com.personal.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 简历 - 基本信息
 *
 * @author 李箎
 */
@Controller
public class ResumeController {

    @Autowired
    private ResumeService service;

    /**
     * 新增/编辑 简历基本信息
     *
     * @param resume 简历基本信息
     * @return
     */
    @RequestMapping("/resume")
    @ResponseBody
    public String saveResume(@RequestBody Resume resume) {
        System.out.println("简历基本信息");
        System.out.println(resume);
        return service.saveResume(resume);
    }

    /**
     * 获得简历基本信息通过用户id
     *
     * @param userId 用户id
     * @return
     */
    @RequestMapping("/getResumeByUserId")
    @ResponseBody
    public Resume getResumeByUserId(@RequestBody long userId) {
        Resume resume = service.getResumeByUserId(userId);
        System.out.println(resume);
        return resume;
    }

    /**
     * 所有投递到本公司的简历概要列表
     *
     * @param map 公司id，当前页，页大小
     * @return
     */
    @RequestMapping("/resumeList")
    @ResponseBody
    public List<ResumeOutline> resumeList(@RequestBody Map<String, Object> map) {
        return service.resumeList(map);
    }

    /**
     * 所有投递到本公司的简历概要列表长度（投递到本公司的简历数）
     *
     * @param map 公司id，当前页，页大小
     * @return
     */
    @RequestMapping("/resumeListLength")
    @ResponseBody
    public int resumeListLength(@RequestBody Map<String, Object> map) {
        return service.resumeList(map).size();
    }

    /**
     * 某职位的所有投递的简历
     *
     * @param positionId 职位id
     * @return
     */
    @RequestMapping("/getResumeNameList")
    @ResponseBody
    public List<Resume> getResumeNameList(@RequestBody long positionId) {
        return service.getResumeNameList(positionId);
    }

    /**
     * 管理员 - 系统中简历总数
     * @return
     */
    @RequestMapping("/resumeCount")
    @ResponseBody
    public List<Resume> resumeCount() {
        return service.resumeCount();
    }
}
