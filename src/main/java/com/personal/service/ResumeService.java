package com.personal.service;

import com.personal.pojo.Resume;
import com.personal.pojo.web.ResumeOutline;
import java.util.List;

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
    public Resume getResumeByUserId(long userId);

    /**
     * 所有投递到本公司的简历概要列表
     * @param companyId 公司id
     * @param currentPage 当前页
     * @param pageSize 每页条数
     * @return
     */
    List<ResumeOutline> resumeList(long companyId, int currentPage, int pageSize);

    /**
     * 所有投递到本公司的简历概要列表长度
     * @param companyId
     * @return
     */
    int resumeListLength(long companyId);
}
