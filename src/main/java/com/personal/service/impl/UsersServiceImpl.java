package com.personal.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.personal.mapper.UsersMapper;
import com.personal.pojo.Employee;
import com.personal.pojo.Users;
import com.personal.redisService.RedisConnection;
import com.personal.redisService.tool.RedisUtil;
import com.personal.service.EmployeeService;
import com.personal.service.UsersService;
import com.personal.util.ConstPool;
import com.personal.util.CookieTool;
import com.personal.util.StrValidate;
import com.personal.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.List;

@SuppressWarnings("ALL")
@Service
@Transactional
public class UsersServiceImpl implements UsersService {
    /***
     * 招聘者用户类型
     */
    public static final int USER_TYPE = 2;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private UsersMapper mapper;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 用户注册
     * 一个人可以有多个邮箱，一个邮箱最多一个人
     * 方式1：微信扫码以获取数据以注册；方式2：填写邮箱发验证码以注册；
     * <p>
     * 注册做的事：数据库增一行，确认身份 用户的身份认证；
     * 招聘者hr只需注册，自动加入到企业，企业有员工表，有新注册的招聘者会根据员工表自动认证、授权、加入公司，可以发布职位；
     *
     * @param users
     * @return
     */
    @Override
    public String register(Users users) {
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
        Users users1 = mapper.selectByEmail(users.getEmail());
        if (users1 != null) {
            return "注册失败，一个邮箱最多只能绑定一个账号";
        }
        users1 = mapper.selectByUsername(users.getUsername());
        if (users1 != null) {
            return ConstPool.USERNAME_EXIST;
        }
        int insert = mapper.insertSelective(users);
        if (insert > 0) {
            return ConstPool.REGISTER_SUCCESS;
        }
        return ConstPool.REGISTER_FAIL;
    }

    /**
     * 用户登录
     *
     * @param users 登录时输入的用户名密码，将来是登录成功者
     * @return
     */
    @Override
    public String login(Users users) {
        //MySQL中的数据
        Users users_MySQL = null;
        /**
         * 用户输入的密码
         */
        String password = users.getPassword();
        if ("2".equals(users.getLoginMode())) {
            users_MySQL = mapper.selectByEmail(users.getEmail());
        } else {
            //查询Redis
            if (redisUtil.get(users.getUsername()) != null) {
                System.out.println("登录验证读取Redis");
                Users users_Redis = (Users) Util.jsonToObject(new Users(), redisUtil.get(users.getUsername()).toString());
                if (users_Redis.getPassword().equals(password)) {
                    users_MySQL = users_Redis;
//                    return ConstPool.LOGIN_SUCCESS;
                } else {
                    return ConstPool.LOGIN_PW_INCORRECT;
                }
            }
            //Redis查不到则读取MySQL
            else {
                System.out.println("登录验证读取MySQL");
                users_MySQL = mapper.selectByUsername(users.getUsername());
            }
        }
        if (users_MySQL == null) {
            return ConstPool.USER_NOT_EXIST;
        }
        if ("1".equals(users.getLoginMode()) && !password.equals(users_MySQL.getPassword())) {
            return ConstPool.LOGIN_PW_INCORRECT;
        }
        System.out.println("从MySQL查到的数据：");
        System.out.println(users_MySQL);
        //参数是引用传递！！！需要保证本方法的参数有值
        users.setId(users_MySQL.getId());
        users.setCompanyId(users_MySQL.getCompanyId());
        users.setUsername(users_MySQL.getUsername());
        users.setPassword(users_MySQL.getPassword());
        users.setUserType(users_MySQL.getUserType());
        users.setUserId(users_MySQL.getUserId());
        users.setEmail(users_MySQL.getEmail());
        users.setAuth(users_MySQL.getAuth());
        //[登录缓存]用户信息
        redisUtil.set(users.getUsername(), Util.objectToJson(users));
        return ConstPool.LOGIN_SUCCESS;
    }

    /**
     * 退出登录
     *
     * @param username 用户名
     * @return
     */
    @Override
    public String logout(String username) {
        System.out.println("用户名：" + username + "退出登录...");
        //清除登录信息的缓存
        redisUtil.delKey(username);
        return "退出登录成功";
    }

    /**
     * 更新用户数据
     *
     * @param users
     * @param flag  0 改username 1 改密码
     * @return 只返回 成功 失败
     */
    @Override
    public String updateUsers(Users users, int flag) {
        if (null == users.getId()) {
            return "失败";
        }
        Users userDb = new Users();
        userDb.setId(users.getId());
        switch (flag) {
            case 0:
                //要改成这个username
                String username = users.getUsername();
                System.out.println("改username");
                if (username.length() <= 5) {
                    users.setUsername(null);
                    return "失败";
                }
                if (mapper.selectByUsername(username) != null) {
                    users.setUsername(null);
                    return "失败";
                }
                userDb.setUsername(username);
                break;
            case 1:
                System.out.println("改 重置 pw");
                String password = users.getPassword();
                String newPassword = users.getNewPassword();
                String newPasswordConfirm = users.getNewPasswordConfirm();
                if (!newPassword.equals(newPasswordConfirm)) {
                    users.setNewPassword(null);
                    return "失败！两次密码不一致";
                }
                if (!StrValidate.isLetterDigit(newPassword)) {
                    users.setNewPassword(null);
                    return "密码同时包含字母和数字8-20位";
                }
                //这是重置密码
                if (password.length() == 0) {
                }
                //这是修改密码
                else if (password.length() != 0) {
                    String login = login(users);
                    if (login.indexOf("成功") == -1) {
                        users.setNewPassword(null);
                        return "原始密码错误";
                    }
                }
                userDb.setPassword(newPassword);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + flag);
        }

        int i = mapper.updateByPrimaryKeySelective(userDb);
        return i > 0 ? "成功" : "失败";
    }

    /**
     * 通过邮箱查询用户
     *
     * @param email
     * @return
     */
    @Override
    public Users selectByEmail(String email) {
        return mapper.selectByEmail(email);
    }

    /**
     * 通过id查询用户
     *
     * @param userId
     * @return
     */
    @Override
    public Users getUserById(long userId) {
        return mapper.selectByPrimaryKey(userId);
    }

    /**
     * 通过用户名查询用户
     *
     * @param username
     * @return
     */
    @Override
    public Users getUserByUsername(String username) {
        return mapper.selectByUsername(username);
    }

    @Override
    public List<Integer> userCount() {
        return mapper.userCount();
    }

    /**
     * 通过用户名得到userId
     *
     * @param username
     * @return
     */
    @Override
    public String getUserIdByUsername(String username) {
        return mapper.selectByUsername(username).getUserId();
    }

    /**
     * 通过userId得到用户名
     *
     * @param userId
     * @return
     */
    @Override
    public String getUsernameByUserId(String userId) {

        return mapper.selectByUserId(userId).getUsername();
    }

    public static void main(String[] args) {
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.delKey("zhouchao");
    }
}
