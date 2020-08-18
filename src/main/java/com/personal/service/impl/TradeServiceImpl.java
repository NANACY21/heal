package com.personal.service.impl;

import com.personal.mapper.Position_enumMapper;
import com.personal.mapper.TradeMapper;
import com.personal.pojo.Position_enum;
import com.personal.pojo.Trade;
import com.personal.pojo.web.Node;
import com.personal.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @author 李箎
 */
@SuppressWarnings("ALL")
@Service
@Transactional
public class TradeServiceImpl implements TradeService {

    /**
     * 自动注入的对象报空指针，是因为启动springBoot项目才会自动注入
     */
    @Autowired
    private TradeMapper tradeMapper;
    @Autowired
    private Position_enumMapper position_enumMapper;

    /**
     * 获得所有行业名称
     * @return
     */
    @Override
    public List<Trade> getAllTrade() {
        return tradeMapper.getAllTrade();
    }

    /**！！！
     * 获得所有行业树
     * 每个行业有行业的划分、职位
     *
     * @return
     */
    @Override
    public List<Node> getAllTradeTreeRoot() {
        //构建与前端匹配的行业数据格式
        ArrayList<Node> allRoot = new ArrayList<>();
        //得到所有行业的id，名称
        ArrayList<Trade> allTrade = tradeMapper.getAllTrade();

//        行遍历行业表
        for (int i = 0; i < allTrade.size(); i++) {
            //行业表第 i 行
            Trade trade = allTrade.get(i);
            //构建一个行业
            Node root = new Node();
            root.setLabel(trade.getName());
            root.setValue(trade.getName());
            //构建这个行业的子节点 - 该行业的所有职位分类
            Vector<Node> allPositionClass = new Vector<>();
            ArrayList<Position_enum> layer2 = position_enumMapper.getNameByTradeId(trade.getId());
            //行遍历该行业的职位分类
            for (int j = 0; j < layer2.size(); j++) {
                Position_enum layer2Name = layer2.get(j);
                Node node1 = new Node();
                node1.setValue(layer2Name.getName());
                node1.setLabel(layer2Name.getName());
                //构建这个职位分类的子节点 - 具体职位
                Vector<Node> node2 = new Vector<>();
                ArrayList<Position_enum> layer3 = position_enumMapper.getNameByParentId(layer2Name.getId());
                //行遍历该职位分类的具体职位
                for (int k = 0; k < layer3.size(); k++) {
                    Node node3 = new Node();
                    Position_enum layer3Name = layer3.get(k);
                    node3.setValue(layer3Name.getName());
                    node3.setLabel(layer3Name.getName());
                    node3.setChildren(null);
                    node2.addElement(node3);
                }
                node1.setChildren(node2);
                allPositionClass.addElement(node1);
            }
            root.setChildren(allPositionClass);
            allRoot.add(root);
        }
        return allRoot;
    }
}
