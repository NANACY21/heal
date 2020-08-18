package com.personal.service.impl;

import com.personal.mapper.UsersMapper;
import com.personal.pojo.Employee;
import com.personal.pojo.Users;
import com.personal.redisService.RedisConnection;
import com.personal.redisService.tool.RedisUtil;
import com.personal.service.EmployeeService;
import com.personal.service.UsersService;
import com.personal.util.ConstPool;
import com.personal.util.CookieTool;
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
        Users users1 = null;
        if ("2".equals(users.getLoginMode())) {
            users1 = mapper.selectByEmail(users.getEmail());
        } else {
            //查询Redis
            if (redisUtil.get(users.getUsername()) != null) {
                System.out.println("登录验证读取Redis");
                if (redisUtil.get(users.getUsername()).equals(users.getPassword())) {
                    return ConstPool.LOGIN_SUCCESS;
                } else {
                    return ConstPool.LOGIN_PW_INCORRECT;
                }
            } else {
                //Redis查不到则读取MySQL
                System.out.println("登录验证读取MySQL");
                users1 = mapper.selectByUsername(users.getUsername());
            }
        }
        if (users1 == null) {
            return ConstPool.USER_NOT_EXIST;
        }
        if ("1".equals(users.getLoginMode()) && !users.getPassword().equals(users1.getPassword())) {
            return ConstPool.LOGIN_PW_INCORRECT;
        }
        System.out.println("从MySQL查到的数据：");
        System.out.println(users1);
        //参数是引用传递！！！
        users.setId(users1.getId());
        users.setCompanyId(users1.getCompanyId());
        users.setUsername(users1.getUsername());
        users.setPassword(users1.getPassword());
        users.setUserType(users1.getUserType());
        users.setUserId(users1.getUserId());
        users.setEmail(users1.getEmail());
        users.setAuth(users1.getAuth());
        //添加登录缓存到Redis
        redisUtil.set(users.getUsername(), users.getPassword());
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
     * @return
     */
    @Override
    public String updateUsers(Users users) {
        String newPassword = users.getNewPassword();
        if (newPassword != null && newPassword.length() != 0) {
            System.out.println("重置密码");
            System.out.println(users);
            String password = users.getPassword();
            String newPasswordConfirm = users.getNewPasswordConfirm();
            if (!newPassword.equals(newPasswordConfirm)) {
                users.setNewPassword(null);
                return "失败！两次密码不一致";
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
            users.setPassword(newPassword);
            //前端空值-长度为0字符串 不存在的属性-null
            users.setCompanyId(null);
            users.setUsername(null);
            users.setUserType(null);
            users.setUserId(null);
            users.setEmail(null);
            users.setNewPassword(null);
        }
        int i = mapper.updateByPrimaryKeySelective(users);
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
}
