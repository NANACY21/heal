package com.personal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.personal.ESService.ElasticSearchUtil;
import com.personal.mapper.PositionMapper;
import com.personal.pojo.Position;
import com.personal.pojo.Trade;
import com.personal.service.TradeService;
import com.personal.util.ConstPool;
import com.personal.util.Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**在这里面测试好使
 * https://blog.csdn.net/qq_27101653/article/details/85072241
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {HealApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class JunitTest {
    @Autowired
    TradeService service;
    @Autowired
    PositionMapper mapper;
    @Autowired
    private ElasticSearchUtil util;
    @Test
    public void test1() {
        List<Trade> allTrade = service.getAllTrade();
        System.out.println(allTrade.size());
    }

    /**
     * 5.1.9.RELEASE
     * 2.1.8.RELEASE
     */
    @Test
    public void getSpringVersion() {
        System.out.println(SpringVersion.getVersion() + "\t" + SpringBootVersion.getVersion());
    }

    @Test
    public void getPosition() {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", 2L);
        List<Position> positions = mapper.selectByCompanyId(map);
        for (Position p : positions) {
            System.out.println(Util.objectToJson(p));
        }
    }

    @Test
    public void ESTest() {
        String ESId = util.getPositionByName("Web前端开发", 3L);
        System.out.println("esid:" + ESId);
    }
}
