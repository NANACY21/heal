package com.personal.service.impl;

import com.personal.mapper.ResumeMapper;
import com.personal.pojo.Resume;
import com.personal.pojo.web.ResumeOutline;
import com.personal.service.ResumeService;
import com.personal.util.Util;
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
public class ResumeServiceImpl implements ResumeService {
    @Autowired
    private ResumeMapper mapper;

    /**
     * 添加/编辑简历
     *
     * @param resume
     * @return
     */
    @Override
    public String saveResume(Resume resume) {
        if (resume.getId() == null) {
            int i = mapper.insertSelective(resume);
            return i > 0 ? "成功" : "失败";
        } else if (resume.getId() != null) {
            int i = mapper.updateByPrimaryKeySelective(resume);
            return i > 0 ? "成功" : "失败";
        }
        return "失败";
    }

    /**
     * @param userId 用户id
     * @return 该用户的简历基本信息
     */
    @Override
    public Resume getResumeByUserId(long userId) {
        return mapper.getResumeByUserId(userId);
    }

    /**
     * 所有投递到本公司的简历概要列表
     * @param companyId 公司id
     * @param currentPage 当前页
     * @param pageSize 每页条数
     * @return
     */
    @Override
    public List<ResumeOutline> resumeList(long companyId, int currentPage, int pageSize) {
        List<ResumeOutline> resumeList = mapper.resumeList(companyId, (currentPage - 1) * pageSize, pageSize);
        //数据改动
        for (ResumeOutline outline : resumeList) {
            int month = Util.differDate(outline.getBegindate(), outline.getEnddate(), "m");
            outline.setWorkLength(month);
            //生日->年龄
            int year = Util.differDate(outline.getBirthday(), Util.getTime(), "y");
            outline.setAge(year);
        }
        return resumeList;
    }

    /**
     * 所有投递到本公司的简历概要列表长度
     *
     * @param companyId
     * @return
     */
    @Override
    public int resumeListLength(long companyId) {
        return mapper.resumeListLength(companyId);
    }
}
