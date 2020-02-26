package com.personal.mapper;

import com.personal.pojo.Eval;

public interface EvalMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Eval record);

    int insertSelective(Eval record);

    Eval selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Eval record);

    int updateByPrimaryKeyWithBLOBs(Eval record);

    int updateByPrimaryKey(Eval record);
}