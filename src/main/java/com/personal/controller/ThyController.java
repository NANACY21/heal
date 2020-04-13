package com.personal.controller;

import com.personal.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * thymeleaf和jsp不能混用
 */
@Controller
public class ThyController {

    @RequestMapping("/test1")
    public String test1(Model model, HttpServletRequest request, HttpSession session) {
        model.addAttribute("mes", "hhhh!!!!hhhhthymeleaf");
        model.addAttribute("sex", "男");
        model.addAttribute("num", "1");


        List<User> users = new ArrayList<>();
        User user1 = new User(1, "user1");
        User user2 = new User(2, "user2");
        User user3 = new User(3, "user3");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        model.addAttribute("users", users);


        Map<String, User> maps = new HashMap<>();
        maps.put("u1", user1);
        maps.put("u2", user2);
        maps.put("u3", user3);
        model.addAttribute("maps", maps);


        request.setAttribute("msg", "request message");
        session.setAttribute("smsg", "session message");
        model.addAttribute("pic", "http://pic33.nipic.com/20131007/13639685_123501617185_2.jpg");
        //跳到该html
        return "index";
    }
}
