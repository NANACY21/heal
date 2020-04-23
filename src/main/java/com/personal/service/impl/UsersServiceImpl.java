package com.personal.service.impl;

import com.personal.mapper.UsersMapper;
import com.personal.pojo.Users;
import com.personal.service.UsersService;
import com.personal.util.ConstPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("ALL")
@Service
@Transactional
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper mapper;

    /**
     * 一个人可以有多个邮箱，
     * 一个邮箱最多一个人
     * @param users
     * @return
     */
    @Override
    public String register(Users users) {
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
            users1 = mapper.selectByUsername(users.getUsername());
        }
        if (users1 == null) {
            return ConstPool.USER_NOT_EXIST;
        }
        if ("1".equals(users.getLoginMode()) && !users.getPassword().equals(users1.getPassword())) {
            return ConstPool.LOGIN_PW_INCORRECT;
        }
        System.out.println("从MySQL查到的数据：");
        System.out.println(users1);
        //参数是引用传递
        users.setId(users1.getId());
        users.setCompanyId(users1.getCompanyId());
        users.setUsername(users1.getUsername());
        users.setPassword(users1.getPassword());
        users.setUserType(users1.getUserType());
        users.setUserId(users1.getUserId());
        users.setEmail(users1.getEmail());
        users.setAuth(users1.getAuth());
        return ConstPool.LOGIN_SUCCESS;
    }

    /**
     * 更新用户数据
     *
     * @param users
     * @return
     */
    @Override
    public String updateUsers(Users users) {
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
