package com.personal;

import com.personal.pojo.Trade;
import com.personal.service.TradeService;
import com.personal.service.impl.TradeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {HealApplication.class})
public class JunitTest {
    @Autowired
    TradeService service;

    @Test
    public void test1() {
        List<Trade> allTrade = service.getAllTrade();
        System.out.println(allTrade.size());
    }
}
