package com.personal.service;

import com.personal.pojo.Edu_exp;

import java.util.List;

/**
 * @author 李箎
 */
public interface Edu_expService {
    /**
     * 添加/编辑一个教育经历
     *
     * @param edu_exp 一个教育经历
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
     * 获得教育经历列表通过简历id
     * @param resumeId 简历id
     * @return
     */
    List<Edu_exp> getEdu_expListByResumeId(long resumeId);
}
