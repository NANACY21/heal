package com.personal.service.impl;

import com.personal.mapper.Edu_expMapper;
import com.personal.pojo.Edu_exp;
import com.personal.service.Edu_expService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 李箎
 */
@SuppressWarnings("ALL")
@Service
@Transactional
public class Edu_expServiceImpl implements Edu_expService {
    @Autowired
    private Edu_expMapper mapper;

    /**
     * 添加/修改教育经历
     *
     * @param edu_exp
     * @return
     */
    @Override
    public String saveEdu_exp(Edu_exp edu_exp) {
        if (edu_exp.getId() == null) {
            int i = mapper.insertSelective(edu_exp);
            if (i > 0) {
                return "成功";
            }
        } else {
            int i = mapper.updateByPrimaryKeySelective(edu_exp);
            if (i > 0) {
                return "成功";
            }
        }
        return "失败";
    }

    /**
     * 删除一个教育经历
     *
     * @param eduExpId 教育经历id
     * @return
     */
    @Override
    public String delEdu_exp(long eduExpId) {
        int i = mapper.deleteByPrimaryKey(eduExpId);
        if (i > 0) {
            return "删除该教育经历成功";
        }
        return "删除该教育经历失败";
    }

    /**
     * 某个简历的教育经历列表
     *
     * @param resumeId
     * @return
     */
    @Override
    public List<Edu_exp> getEdu_expListByResumeId(long resumeId) {
        return mapper.getEdu_expListByResumeId(resumeId);
    }
}
