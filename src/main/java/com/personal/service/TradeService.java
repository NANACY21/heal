package com.personal.service;

import com.personal.pojo.Trade;
import com.personal.pojo.web.Node;

import java.util.List;

/**
 * @author 李箎
 */
public interface TradeService {

    /**
     * 获得所有行业名称
     *
     * @return
     */
    List<Trade> getAllTrade();

    /**！！！
     * 获得所有行业树
     * 每个行业有行业的划分、职位
     * @return
     */
    List<Node> getAllTradeTreeRoot();
}
