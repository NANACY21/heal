package com.personal.mapper;

import com.personal.pojo.Position;
import com.personal.pojo.web.ReleasePosition;

import java.util.List;

/**
 * @author 李箎
 */
public interface PositionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Position record);

    int insertSelective(Position record);

    Position selectByPrimaryKey(Long id);
    List<Position> selectByCompanyId(Long companyId);

    List<ReleasePosition> selectByStatus(int status, long rowNum);

    int updateByPrimaryKeySelective(Position record);

    int updateByPrimaryKey(Position record);
}