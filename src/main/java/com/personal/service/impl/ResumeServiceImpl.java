package com.personal.service.impl;

import com.personal.mapper.ResumeMapper;
import com.personal.pojo.Resume;
import com.personal.pojo.web.ResumeOutline;
import com.personal.service.ResumeService;
import com.personal.util.Util;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

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
     *
     * @param map 公司id 当前页 每页条数
     * @return
     */
    @Override
    public List<ResumeOutline> resumeList(Map<String, Object> map) {
        //！！！
        if (map.get("currentPage") != null && map.get("pageSize") != null) {
            int currentPage = Integer.parseInt(map.get("currentPage").toString());
            int pageSize = Integer.parseInt(map.get("pageSize").toString());
            map.put("beginRowIndex", (currentPage - 1) * pageSize);
            map.put("rowNum", pageSize);
        }
        List<ResumeOutline> resumeList = mapper.resumeList(map);
        //数据改动
        for (ResumeOutline outline : resumeList) {
            //int month = Util.differDate(outline.getBegindate(), outline.getEnddate(), "m");
            //outline.setWorkLength(month);
            //生日->年龄
            int year = Util.differDate(outline.getBirthday(), Util.getTime(), "y");
            outline.setAge(year);
        }
        return resumeList;
    }

    /**
     * 某职位的所有投递的简历
     *
     * @param positionId
     * @return
     */
    @Override
    public List<Resume> getResumeNameList(long positionId) {
        return mapper.getResumeNameList(positionId);
    }

    /**
     * 简历数
     *
     * @return
     */
    @Override
    public List<Resume> resumeCount() {
        return mapper.resumeCount();
    }
}
