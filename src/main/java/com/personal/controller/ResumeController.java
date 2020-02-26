package com.personal.controller;

import com.personal.pojo.Resume;
import com.personal.pojo.web.ResumeOutline;
import com.personal.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**简历
 * @author 李箎
 */
@Controller
public class ResumeController {

    @Autowired
    private ResumeService service;
    /**
     * 新增/编辑简历
     * @param resume
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
     * 用户简历基本信息
     *
     * @param userId
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
     * @param
     * @return
     */
    @RequestMapping("/resumeList")
    @ResponseBody
    public List<ResumeOutline> resumeList(@RequestBody Map<String, Object> map) {
        if (map == null) {
            return new ArrayList<ResumeOutline>();
        }
        long companyId = Long.parseLong(map.get("companyId").toString());
        int currentPage = Integer.parseInt(map.get("currentPage").toString());
        int pageSize = Integer.parseInt(map.get("pageSize").toString());
        return service.resumeList(companyId, currentPage, pageSize);
    }

    /**
     * 所有投递到本公司的简历概要列表长度
     * @param companyId
     * @return
     */
    @RequestMapping("/resumeListLength")
    @ResponseBody
    public int resumeListLength(@RequestBody long companyId) {
        return service.resumeListLength(companyId);
    }
}
