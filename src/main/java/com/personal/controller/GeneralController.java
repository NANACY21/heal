package com.personal.controller;

import com.personal.pojo.Users;
import com.personal.service.UsersService;
import com.personal.util.ConstPool;
import com.personal.util.CookieTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 通用
 *
 * @author 李箎
 */
@Controller
public class GeneralController {
    @Autowired
    private UsersService service;
    /**
     * 验证是否已登录，如果已登录，返回用户名
     * 查询 读cookie
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/isLogged")
    @ResponseBody
    public Users isLogged(HttpServletRequest request) {
        Map<String, String> cookieMap = CookieTool.readCookieMap(request);
        Users users = new Users();
        if (cookieMap.containsKey(ConstPool.SESSION_KEY1) && cookieMap.containsKey(ConstPool.SESSION_KEY2)) {
            users.setUsername(cookieMap.get(ConstPool.SESSION_KEY1).toString());
            users.setPassword(cookieMap.get(ConstPool.SESSION_KEY2).toString());
            String login = service.login(users);
            if (ConstPool.LOGIN_SUCCESS.equals(login)) {
                users.setPassword(null);
                //这时添加session ！！！
                if (request.getSession(false) == null) {
                    HttpSession session = request.getSession();
                    session.setAttribute(ConstPool.SESSION_KEY1, users.getUsername());
                    session.setAttribute(ConstPool.SESSION_KEY2, users.getPassword());
                }
                System.out.println("用户在本PC的登录状态还在，发到前端的数据：");
                System.out.println(users);
                return users;
            }
        }
        return null;
    }

    /**用这个有问题
     * 查询 读session
     * 获取用户
     * @return
     */
    @RequestMapping("/getCurrentUser")
    @ResponseBody
    public Users getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = session.getAttribute(ConstPool.SESSION_KEY1).toString();
        String password = session.getAttribute(ConstPool.SESSION_KEY2).toString();
        System.out.println(username + password);
        Users users = new Users(username, password, null);
        System.out.println(users);
        String login = service.login(users);
        if (ConstPool.LOGIN_SUCCESS.equals(login)) {
            users.setPassword(null);
            System.out.println("读取session，发到前端的数据：");
            System.out.println(users);
        }
        return users;
    }
}
