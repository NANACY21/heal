package com.personal.mapper;

import com.personal.pojo.Users;

import java.util.List;

/**
 * @author 李箎
 */
public interface UsersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Long id);

    /**
     * 根据username查询
     *
     * @param username 用户名
     * @return
     */
    Users selectByUsername(String username);

    /**
     * 根据userId查询 19位随机数
     * @param userId
     * @return
     */
    Users selectByUserId(String userId);
    Users selectByEmail(String email);

    /**
     * 修改账号信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    List<Integer> userCount();
}