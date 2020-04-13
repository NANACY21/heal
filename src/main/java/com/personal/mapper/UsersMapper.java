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

    Users selectByUsername(String username);
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