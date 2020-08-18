package com.personal.service;
import com.personal.pojo.Work_exp;

import java.util.List;

public interface Work_expService {
    /**
     * 新增/编辑一个工作经历
     *
     * @param work_exp 一个工作经历
     * @return
     */
    String saveWork_exp(Work_exp work_exp);

    /**
     * 删除一个工作经历
     * @param workExpId 工作经历id
     * @return
     */
    String delWork_exp(long workExpId);

    /**
     * 获得简历的工作经历列表通过简历id
     * @param resumeId 简历id
     * @return
     */
    List<Work_exp> getWork_expListByResumeId(long resumeId);
}
