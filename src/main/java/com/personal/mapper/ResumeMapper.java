package com.personal.mapper;

import com.personal.pojo.Resume;
import com.personal.pojo.web.ResumeOutline;

import java.util.List;

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
     * @param companyId
     * @param beginRowIndex
     * @param rowNum
     * @return
     */
    List<ResumeOutline> resumeList(Long companyId, int beginRowIndex, int rowNum);

    /**
     * 所有投递到本公司的简历概要列表的长度
     * @param companyId
     * @return
     */
    int resumeListLength(Long companyId);
}