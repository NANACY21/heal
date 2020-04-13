package com.personal.service;

import com.personal.pojo.Edu_exp;

import java.util.List;

/**
 * @author 李箎
 */
public interface Edu_expService {
    /**
     * 添加/修改教育经历
     *
     * @param edu_exp
     * @return
     */
    String saveEdu_exp(Edu_exp edu_exp);

    /**
     * 删除一个教育经历
     * @param eduExpId 教育经历id
     * @return
     */
    String delEdu_exp(long eduExpId);

    /**
     * 某个简历的教育经历列表
     * @param resumeId
     * @return
     */
    List<Edu_exp> getEdu_expListByResumeId(long resumeId);
}
