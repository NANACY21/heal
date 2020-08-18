package com.personal.service;

import com.personal.pojo.Position;
import com.personal.pojo.Resume;
import com.personal.pojo.web.ResumeOutline;
import java.util.List;
import java.util.Map;

/**
 * @author 李箎
 */
public interface ResumeService {
    /**
     * 新增/编辑 简历基本信息
     * @param resume 简历基本信息
     * @return
     */
    public String saveResume(Resume resume);

    /**
     * 获得简历基本信息通过用户id
     * @param userId 用户id
     * @return
     */
    Resume getResumeByUserId(long userId);

    /**
     * 所有投递到本公司的简历概要列表
     *
     * @param map 公司id，当前页，每页条数
     * @return
     */
    List<ResumeOutline> resumeList(Map<String, Object> map);

    /**
     * 某职位的所有投递的简历
     * @param positionId 职位id
     * @return
     */
    List<Resume> getResumeNameList(long positionId);

    /**
     * 管理员 - 系统中简历总数
     * @return
     */
    List<Resume> resumeCount();
}
