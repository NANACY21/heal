package com.personal.service;
import com.personal.pojo.Work_exp;

import java.util.List;

public interface Work_expService {
    /**
     * 添加/修改工作经历
     *
     * @param work_exp
     * @return
     */
    String saveWork_exp(Work_exp work_exp);

    /**
     * 某个简历的工作经历列表
     * @param resumeId
     * @return
     */
    List<Work_exp> getWork_expListByResumeId(long resumeId);
}
