package com.personal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 单纯的供测试的控制器
 */
@Controller
public class Error1Controller {

    @RequestMapping("/e_test3")
    public String test3() {
        System.out.println(1 / 0);
        return "index";
    }

    @RequestMapping("/e_test4")
    public String test4() {
        String str = null;
        str.length();
        return "index";
    }
}
