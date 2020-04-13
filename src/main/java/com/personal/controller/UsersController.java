package com.personal.controller;

import com.personal.kafkaService.consumer.MessageConsumer;
import com.personal.kafkaService.producer.SystemMessageProducer;
import com.personal.kafkaService.tool.Util;
import com.personal.pojo.Employee;
import com.personal.pojo.Users;
import com.personal.pojo.msg.Message;
import com.personal.redisService.RedisConnection;
import com.personal.redisService.tool.RedisUtil;
import com.personal.service.EmployeeService;
import com.personal.service.PositionService;
import com.personal.service.UsersService;
import com.personal.util.ConstPool;
import com.personal.util.CookieTool;
import com.personal.util.HttpSessionListenerImpl;
import com.personal.util.OnlineUserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private PositionService positionService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 用户注册
     * 注意：不存在的邮箱、不存在的验证码
     * 用户的身份认证
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
        //招聘者验证！！！
        if (users.getUserType() == USER_TYPE) {
            System.out.println("[招聘者验证]");
            Employee employeeByEmail = employeeService.getEmployeeByEmail(users.getEmail());
            //不是员工也让注册
            if (employeeByEmail == null) {
                System.out.println("不是员工");
            } else {
                //招聘者验证成功 员工认证 先有公司->再有员工->再有招聘者用户
                users.setCompanyId(employeeByEmail.getCompanyId());
                //读取用户身份 进行用户身份认证！！！
                users.setAuth(employeeByEmail.getAuth());
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
     * Redis也需要修改
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
        String newPassword = users.getNewPassword();
        String newPasswordConfirm = users.getNewPasswordConfirm();
        if (!newPassword.equals(newPasswordConfirm)) {
            users.setNewPassword(null);
            return "失败，两次密码不一致";
        }
        //这是重置密码
        if (password.length() == 0) {
        }
        //这是修改密码
        else if (password.length() != 0) {
            String login = service.login(users);
            if (login.indexOf("成功") == -1) {
                users.setNewPassword(null);
                return "原始密码错误";
            }
        }
        users.setPassword(newPassword);
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
        users.setNewPassword(null);
        return "用户登录密码重置失败";
    }

    /**
     * 修改用户名
     * Redis、Kafka也需要修改
     * 头像、简历照片文件名不用修改
     * ES中有数据就改 没有就不改
     * 前端空值-长度为0字符串 不存在的属性-null
     *
     * @param users
     * @return
     */
    @RequestMapping("/updateUsername")
    @ResponseBody
    public String updateUsername(@RequestBody Users users) {
        return "用户名修改" + service.updateUsers(users);
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
        if (null == users) {
            return message.getTo() + "邮箱的用户不存在";
        }
        //发送这条消息
        Message message1 = new Message(message.getFrom(), users.getUsername(), message.getMsgContent());
        producer.send(ConstPool.KAFKA_TOPIC1, com.personal.util.Util.objectToJson(message1));
        return ConstPool.SUCCESS;
    }

    /**
     * 改变求职者职位的投递状态
     * @param map 用户id 公司id 要改成的状态
     * @return
     */
    @RequestMapping("/changePostStatus")
    @ResponseBody
    public int changePostStatus(@RequestBody Map<String, Object> map) {
        Long userId = Long.valueOf(map.get("userId").toString());
        Long companyId = Long.valueOf(map.get("companyId").toString());
        int postStatus = Integer.parseInt(map.get("postStatus").toString());
        return positionService.changePostStatus(userId, companyId, postStatus);
    }

    /**
     * 从投递箱移除
     * @param map
     * @return
     */
    @RequestMapping("/deletePost")
    @ResponseBody
    public String deletePost(@RequestBody Map<String, Object> map) {
        return positionService.deletePost(map);
    }

    /**
     * 消息列表
     *
     * @param map
     * @return
     */
    @RequestMapping("/messageList")
    @ResponseBody
    public Map<String, List<Message>> messageList(@RequestBody Map<String, Object> map) {
        String username = map.get("username").toString();
        Map<String, List<Message>> msgMap = consumer.receiveLatestMessage(Util.getKafkaConfigProp(), ConstPool.KAFKA_TOPIC1, 100, username);
        if (msgMap.size() == 0) {
            msgMap = new HashMap<>();
        }
        if (map.size() != 1) {
            String withUsername = map.get("withUsername").toString();
            if (null != withUsername && withUsername.length() != 0 && !msgMap.containsKey(withUsername)) {
                msgMap.put(withUsername, new ArrayList<>());
            }
        }
        return msgMap;
    }

    /**
     * 用户个数
     * @return
     */
    @RequestMapping("/userCount")
    @ResponseBody
    public List<Integer> userCount() {
        return service.userCount();
    }

    /**
     * 已登录在线用户
     *
     * @return
     */
    @RequestMapping("/onlineCount")
    @ResponseBody
    public String onlineCount() {
        return OnlineUserServer.onlineUserCount() + "";
    }
}
