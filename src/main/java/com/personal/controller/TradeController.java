package com.personal.controller;

import com.personal.pojo.Trade;
import com.personal.pojo.web.Node;
import com.personal.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 李箎
 */
@Controller
//前端地址以跨域
//@CrossOrigin(origins = {"http://127.0.0.1:8085"})
public class TradeController {

    @Autowired
    private TradeService service;

    //    @RequestMapping("/getAllTrade")
//    @CrossOrigin
    @GetMapping(value = {"getAllTrade"})
    @ResponseBody
    public List<Trade> getAllTrade(Model model) {
        return service.getAllTrade();
    }

    @GetMapping(value = {"getAllTradeTreeRoot"})
    @ResponseBody
    public List<Node> getAllTradeTreeRoot(Model model) {
        return service.getAllTradeTreeRoot();
    }
}
