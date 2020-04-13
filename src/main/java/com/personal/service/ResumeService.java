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
     * 添加/编辑简历
     * @param resume
     * @return
     */
    public String saveResume(Resume resume);

    /**
     * @param userId 用户id
     * @return 该用户的简历基本信息
     */
    Resume getResumeByUserId(long userId);

    /**
     * 所有投递到本公司的简历概要列表
     *
     * @param map 公司id 当前页 每页条数
     * @return
     */
    List<ResumeOutline> resumeList(Map<String, Object> map);

    /**
     * 某职位的所有投递的简历
     * @param positionId
     * @return
     */
    List<Resume> getResumeNameList(long positionId);

    /**
     * 简历数
     * @return
     */
    List<Resume> resumeCount();
}
