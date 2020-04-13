package com.personal.controller;

import com.personal.pojo.Users;
import com.personal.service.MailService;
import com.personal.util.RestResponse;
import com.personal.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * 邮件服务
 *
 * @author 李箎
 */
//@Slf4j
//@RestController
@Controller
@EnableRedisHttpSession
@RequestMapping("/mail")
public class MailController {

    /**
     * 验证码5分钟失效
     */
    public static final int INT = 5;
    @Autowired
    private MailService mailService;

    /**请求url：http://localhost:8088/mail/getCheckCode?users={...}
     * @param users
     * @return 原来返回值为RestResponse<String>
     */
    @RequestMapping(value = "/getCheckCode", method = RequestMethod.POST)
    @ResponseBody
    public String getCheckCode(@RequestBody Users users, HttpServletRequest request) {
        RestResponse<String> restResponse = new RestResponse<>();
        //生成6位的验证码
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        System.out.println("生成的验证码为：" + checkCode);
        //验证码存储到session，session存储到Redis
        HttpSession session = request.getSession();
        //key为邮箱地址，value为验证码 覆盖原来的验证码
        session.setAttribute(users.getEmail(), checkCode);

        //设置验证码失效时间
        session.setMaxInactiveInterval(60 * INT);
        //发邮件以告诉用户验证码
        String message = "你的注册验证码：" + checkCode + "，该验证码在：" + Util.getDate(new Date(), 5) + "前失效。";
        try {
            mailService.sendSimpleMail(users.getEmail(), "exodus - 用户注册验证码", message);
        } catch (Exception e) {
            restResponse.setData(e.toString());
            //生成的验证码销毁
            session.setAttribute(users.getEmail(), null);
            //可能是因为邮箱不存在
            return "验证码发送失败";
        }
        restResponse.setData(checkCode);
//        验证码不能发给前端，告诉前端验证码已发送即可
        return "验证码已发送至您的邮箱";
    }
}
