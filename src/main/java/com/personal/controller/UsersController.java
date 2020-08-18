package com.personal.controller;

import com.personal.kafkaService.consumer.MessageConsumer;
import com.personal.kafkaService.producer.SystemMessageProducer;
import com.personal.kafkaService.tool.Util;
import com.personal.pojo.Users;
import com.personal.pojo.msg.Message;
import com.personal.service.PositionService;
import com.personal.service.UsersService;
import com.personal.util.ConstPool;
import com.personal.util.CookieTool;
import com.personal.util.OnlineUserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户
 *
 * @author 李箎
 */
@Controller
public class UsersController {

    @Autowired
    private UsersService service;
    @Autowired
    private SystemMessageProducer producer;
    @Autowired
    private MessageConsumer consumer;
    @Autowired
    private PositionService positionService;

    /**
     * 用户注册
     * 注意：不存在的邮箱、不存在的验证码
     *
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
        return service.register(users);
    }

    /**
     * 用户登录
     * 注意：不存在的邮箱、不存在的验证码
     *
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
        }
        String login = service.login(users);
        if (ConstPool.LOGIN_SUCCESS.equals(login)) {
            CookieTool.batchAddCookie(response, ConstPool.SESSION_KEY1, users.getUsername(), ConstPool.SESSION_KEY2, users.getPassword());
        }
        return login;
    }

    /**
     * 删除cookie
     *
     * @param username
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public String logout(@RequestBody String username, HttpServletRequest request, HttpServletResponse response) {
        CookieTool.batchDeleteCookie(request, response, ConstPool.SESSION_KEY1, ConstPool.SESSION_KEY2);
        return service.logout(username);
    }

    @RequestMapping("/getAllMsg")
    @ResponseBody
    public String getAllMsg() {
        return "66";
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
        return "用户登录密码重置" + service.updateUsers(users);
    }

    /**
     * 修改用户名
     * Redis也需要修改
     * Kafka不需要修改
     * Cookie存的用户名也得改
     * 头像、简历照片文件名不用修改
     * ES中有数据就改 没有就不改
     * 前端空值-长度为0字符串 不存在的属性-null
     *
     * @param users
     * @return
     */
    @RequestMapping("/updateUsername")
    @ResponseBody
    public String updateUsername(@RequestBody Users users, HttpServletRequest request, HttpServletResponse response) {
        CookieTool.batchDeleteCookie(request, response, ConstPool.SESSION_KEY1);
        CookieTool.batchAddCookie(response, ConstPool.SESSION_KEY1, users.getUsername());
        return "用户名修改" + service.updateUsers(users);
    }

    /**
     * hr向求职者打招呼
     *
     * @param message
     * @return
     */
    @RequestMapping("/sayHello")
    @ResponseBody
    public String sayHello(@RequestBody Message message) {
        System.out.println(message);
        //发送者用户名
        String from = message.getFrom();
        Users users = service.selectByEmail(message.getTo());
        if (null == users) {
            return message.getTo() + "邮箱的用户不存在";
        }
        //接收者用户名
        String to = users.getUsername();
        //发送这条消息
        Message message1 = new Message(from, to, message.getContent());
        message1.setFromId(service.getUserIdByUsername(from));
        message1.setToId(service.getUserIdByUsername(to));
        producer.send(ConstPool.KAFKA_TOPIC1, com.personal.util.Util.objectToJson(message1));
        return ConstPool.SUCCESS;
    }

    /**
     * 改变求职者职位的投递状态
     *
     * @param map 用户id 公司id 要改成的状态
     * @return
     */
    @RequestMapping("/changePostStatus")
    @ResponseBody
    public int changePostStatus(@RequestBody Map<String, Object> map) {
        return positionService.changePostStatus(map);
    }

    /**
     * 从投递箱移除一个职位
     *
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
     * @param map 我的用户名 对方用户名
     * @return map类型 key 用户名
     */
    @RequestMapping("/messageList")
    @ResponseBody
    public Map<String, List<Message>> messageList(@RequestBody Map<String, Object> map) {
        //我的用户名
        String username = map.get("username").toString();
        Map<String, List<Message>> msgMap = consumer.receiveLatestMessage(Util.getKafkaConfigProp(), ConstPool.KAFKA_TOPIC1, 100, username);
        if (map.size() != 1) {
            //对方用户名
            String withUsername = map.get("withUsername").toString();
            if (null != withUsername && withUsername.length() != 0 && !msgMap.containsKey(withUsername)) {
                msgMap.put(withUsername, new ArrayList<>());
            }
        }
        return msgMap;
    }

    /**
     * 用户个数
     *
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
