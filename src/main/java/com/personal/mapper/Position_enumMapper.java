package com.personal.mapper;

import com.personal.pojo.Position_enum;
import com.personal.pojo.Trade;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
public interface Position_enumMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Position_enum record);

    int insertSelective(Position_enum record);

    Position_enum selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Position_enum record);

    int updateByPrimaryKey(Position_enum record);

    ArrayList<Position_enum> getAllPosition_enum();

    /**
     * 查询某行业的所有职位分类的id，名称
     * @param tradeId 行业id
     * @return
     */
    ArrayList<Position_enum> getNameByTradeId(Long tradeId);

    /**
     * 查询某职位分类的所有职位名称
     * @param parentId 行业划分id
     * @return
     */
    ArrayList<Position_enum> getNameByParentId(Long parentId);
}