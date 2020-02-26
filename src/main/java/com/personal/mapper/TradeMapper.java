package com.personal.mapper;

import com.personal.pojo.Trade;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author 李箎
 */
@Component
public interface TradeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Trade record);

    int insertSelective(Trade record);

    Trade selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Trade record);

    int updateByPrimaryKey(Trade record);

    ArrayList<Trade> getAllTrade();
}