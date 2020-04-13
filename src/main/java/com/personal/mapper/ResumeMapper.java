package com.personal.mapper;

import com.personal.pojo.Resume;
import com.personal.pojo.web.ResumeOutline;

import java.util.List;
import java.util.Map;

public interface ResumeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Resume record);

    int insertSelective(Resume record);

    Resume selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Resume record);

    int updateByPrimaryKeyWithBLOBs(Resume record);

    int updateByPrimaryKey(Resume record);

    /**一个用户只有一个简历基本信息
     * 查询用户基本简历
     * @param userId
     * @return
     */
    Resume getResumeByUserId(Long userId);

    /**
     * 所有投递到本公司的简历概要列表
     * @param map
     * @return
     */
    List<ResumeOutline> resumeList(Map<String, Object> map);

    /**
     * 某职位的所有投递的简历
     * @param positionId
     * @return
     */
    List<Resume> getResumeNameList(Long positionId);

    List<Resume> resumeCount();
}