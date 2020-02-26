package com.personal.controller;

import com.personal.kafkaService.consumer.MessageConsumer;
import com.personal.kafkaService.producer.SystemMessageProducer;
import com.personal.kafkaService.tool.Util;

import com.personal.mapper.UsersMapper;
import com.personal.pojo.Employee;
import com.personal.pojo.Users;
import com.personal.pojo.msg.Message;
import com.personal.redisService.RedisConnection;
import com.personal.service.EmployeeService;
import com.personal.service.UsersService;
import com.personal.util.ConstPool;
import com.personal.util.CookieTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 李箎
 */
@Controller
public class UsersController {
    /***
     * 招聘者
     */
    public static final int USER_TYPE = 2;
    @Autowired
    private UsersService service;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private SystemMessageProducer producer;
    @Autowired
    private MessageConsumer consumer;

    /**
     * 用户注册
     * 注意：不存在的邮箱、不存在的验证码
     * @param users 接收JSON串，转实体类
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public String register(@RequestBody Users users, HttpServletRequest request) {
        System.out.println("用户提供的个人信息用来注册：");
        System.out.println(users);
        //前端输入的验证码得拿到后端来验证
        String code = users.getCode();
        System.out.println("前端输入的验证码：" + code);
        //读取Redis中的key
        HttpSession session = request.getSession();
        //得到验证码后又临时换邮箱
        if (session == null || session.getAttribute(users.getEmail()) == null) {
            return "验证码无效";
        }
        String checkCode = session.getAttribute(users.getEmail()).toString();
        System.out.println("Redis中保存的验证码：" + checkCode);
        if (!checkCode.equals(code)) {
            return "验证码错误，注册失败";
        }
        //为用户生成用户号，用户可以修改，用户号唯一
        users.setUserId(com.personal.util.Util.getGlobalUniqueId());
        System.out.println(users);
        //[招聘者验证]
        if (users.getUserType() == USER_TYPE) {
            System.out.println("[招聘者验证]");
            Employee employeeByEmail = employeeService.getEmployeeByEmail(users.getEmail());
            //不是员工也让注册
            if (employeeByEmail == null) {
                System.out.println("不是员工");
            } else {
                //招聘者验证成功 员工认证 先有公司->再有员工->再有招聘者用户
                users.setCompanyId(employeeByEmail.getCompanyId());
            }
        }
        return service.register(users);
    }

    /**
     * 用户登录
     * 注意：不存在的邮箱、不存在的验证码
     * @param users
     * @param response
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public String login(@RequestBody Users users, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("登录 用户提供的数据：");
        System.out.println(users);
        //用户是邮箱登录
        if ("2".equals(users.getLoginMode())) {
            //读取Redis中的key
            HttpSession session = request.getSession();
            //不存在的验证码
            if (session == null || session.getAttribute(users.getEmail()) == null) {
                return "验证码无效";
            }
            String checkCode = session.getAttribute(users.getEmail()).toString();
            System.out.println("Redis中保存的验证码：" + checkCode);
            if (!checkCode.equals(users.getCode())) {
                return "验证码错误，登录失败";
            }
            String login = service.login(users);
            if (ConstPool.LOGIN_SUCCESS.equals(login)) {
                CookieTool.batchAddCookie(response, ConstPool.SESSION_KEY1, users.getUsername(), ConstPool.SESSION_KEY2, users.getPassword());
                return ConstPool.LOGIN_SUCCESS;
            }
            return login;
        }
        Jedis jedis = RedisConnection.getJedis();
        //查询Redis
        if (jedis.get(users.getUsername()) != null) {
            System.out.println("登录验证读取Redis");
            if (jedis.get(users.getUsername()).equals(users.getPassword())) {
                CookieTool.batchAddCookie(response, ConstPool.SESSION_KEY1, users.getUsername(), ConstPool.SESSION_KEY2, users.getPassword());
                return ConstPool.LOGIN_SUCCESS;
            } else {
                return ConstPool.LOGIN_PW_INCORRECT;
            }
        } else {
            //Redis查不到则读取MySQL
            String login = service.login(users);
            System.out.println("登录验证读取MySQL");
            if (ConstPool.LOGIN_SUCCESS.equals(login)) {
                //添加登录缓存到Redis
                jedis.set(users.getUsername(), users.getPassword());
                CookieTool.batchAddCookie(response, ConstPool.SESSION_KEY1, users.getUsername(), ConstPool.SESSION_KEY2, users.getPassword());
                return ConstPool.LOGIN_SUCCESS;
            }
            return login;
        }
    }

    /**
     * 删除cookie
     * @param username
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public String logout(@RequestBody String username, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("用户名：" + username + "退出登录...");
        Jedis jedis = RedisConnection.getJedis();
        CookieTool.batchDeleteCookie(request, response, ConstPool.SESSION_KEY1, ConstPool.SESSION_KEY2);
        //清除登录信息的缓存
        Long del = jedis.del(username);
        return "退出登录成功";
    }

    @RequestMapping("/getAllMsg")
    @ResponseBody
    public String getAllMsg() {
        return "71";
    }

    /**
     * 重置/修改密码
     *
     * @param users
     * @return
     */
    @RequestMapping("/updatePassword")
    @ResponseBody
    public String updatePassword(@RequestBody Users users) {
        System.out.println("重置密码");
        System.out.println(users);
        String password = users.getPassword();
        //这是重置密码
        if (password.length() == 0) {

        }
        //这是修改密码
        else if (password.length() != 0) {
            String login = service.login(users);
            if (login.indexOf("成功") == -1) {
                return "原始密码错误";
            }
        }
        users.setPassword(users.getNewPassword());
        //前端空值-长度为0字符串 不存在的属性-null
        users.setCompanyId(null);
        users.setUsername(null);
        users.setUserType(null);
        users.setUserId(null);
        users.setEmail(null);
        String s = service.updateUsers(users);
        if ("成功".equals(s)) {
            return "用户登录密码重置成功";
        }
        return "用户登录密码重置失败";
    }
    /**
     * 修改用户名
     *
     * @param users
     * @return
     */
    @RequestMapping("/updateUsername")
    @ResponseBody
    public String updateUsername(@RequestBody Users users) {
        System.out.println("修改用户名");
        System.out.println(users);
        users.setUsername(users.getUsername());
        //前端空值-长度为0字符串 不存在的属性-null
        users.setCompanyId(null);
        users.setPassword(null);
        users.setUserType(null);
        users.setUserId(null);
        users.setEmail(null);
        String s = service.updateUsers(users);
        if ("成功".equals(s)) {
            return "用户名修改成功";
        }
        return "用户名修改失败";
    }

    /**
     * hr向求职者打招呼
     * @param message
     * @return
     */
    @RequestMapping("/sayHello")
    @ResponseBody
    public String sayHello(@RequestBody Message message) {
        System.out.println(message);
        Users users = service.selectByEmail(message.getTo());
        //发送这条消息
        Message message1 = new Message(message.getFrom(), users.getUsername(), message.getData());
        producer.send("postResume", com.personal.util.Util.objectToJson(message1));
        return ConstPool.SUCCESS;
    }

    /**
     * 消息列表
     *
     * @param username
     * @return
     */
    @RequestMapping("/messageList")
    @ResponseBody
    public List<Message> messageList(@RequestBody String username) {
        return consumer.receiveLatestMessage(Util.getKafkaConfigProp(), "postResume", 30, username);
    }
}
