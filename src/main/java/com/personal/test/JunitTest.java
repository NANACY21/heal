package com.personal.test;

import com.personal.HealApplication;
import com.personal.pojo.NUser;
import com.personal.service.NUserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {HealApplication.class})
public class JunitTest {

    @Autowired
    private NUserService usersService;

    @Test
    public void test1() {
        List<NUser> users = usersService.getAllNUser();
        for (NUser u : users) {
            System.out.println(u);
        }
    }
}
