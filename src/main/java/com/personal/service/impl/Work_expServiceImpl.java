package com.personal.service.impl;

import com.personal.mapper.Work_expMapper;
import com.personal.pojo.Work_exp;
import com.personal.service.Work_expService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("ALL")
@Service
@Transactional
public class Work_expServiceImpl implements Work_expService {
    @Autowired
    private Work_expMapper mapper;

    /**
     * 添加/修改工作经历
     *
     * @param work_exp
     * @return
     */
    @Override
    public String saveWork_exp(Work_exp work_exp) {
        if (work_exp.getEnddate().length() == 0) {
            work_exp.setEnddate("至今");
        }
        if (work_exp.getId() == null) {
            int i = mapper.insertSelective(work_exp);
            if (i > 0) {
                return "成功";
            }
        } else {
            int i = mapper.updateByPrimaryKeySelective(work_exp);
            if (i > 0) {
                return "成功";
            }
        }
        return "失败";
    }

    /**
     * 删除一个工作经历
     *
     * @param workExpId 工作经历id
     * @return
     */
    @Override
    public String delWork_exp(long workExpId) {
        int i = mapper.deleteByPrimaryKey(workExpId);
        String temp = "删除工作经历";
        if (i > 0) {
            temp += "成功";
        } else {
            temp += "失败";
        }
        return temp;
    }

    /**
     * 某个简历的工作经历列表
     *
     * @param resumeId
     * @return
     */
    @Override
    public List<Work_exp> getWork_expListByResumeId(long resumeId) {
        return mapper.getWork_expListByResumeId(resumeId);
    }
}
