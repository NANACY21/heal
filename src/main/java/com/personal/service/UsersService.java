package com.personal.service;
import com.personal.pojo.Users;

import java.util.List;

/**
 * @author 李箎
 */
public interface UsersService {

    /**
     * 用户注册
     * 方式1：微信扫码以获取数据以注册；方式2：填写邮箱发验证码以注册；
     *
     * 注册做的事：数据库增一行，确认身份；
     *
     * @param users
     * @return
     */
    String register(Users users);

    /**
     * 用户登录
     * 一定获取用户名
     *
     * 首次登录时：查询MySQL，若登录成功将登录信息存进Redis
     *
     * @param users
     * @return
     */
    String login(Users users);

    /**
     * 更新用户数据
     *
     * @param users
     * @return
     */
    String updateUsers(Users users);

    /**
     * 通过邮箱查询用户
     * @param email
     * @return
     */
    Users selectByEmail(String email);

    /**
     * 通过id查询用户
     * @param userId
     * @return
     */
    Users getUserById(long userId);

    /**
     * 通过用户名查询用户
     *
     * @param username
     * @return
     */
    Users getUserByUsername(String username);

    List<Integer> userCount();

    /**
     * 通过用户名得到userId
     *
     * @param username
     * @return
     */
    String getUserIdByUsername(String username);

    /**
     * 通过userId得到用户名
     * @param userId
     * @return
     */
    String getUsernameByUserId(String userId);
}
