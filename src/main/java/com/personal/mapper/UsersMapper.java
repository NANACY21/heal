package com.personal.mapper;

import com.personal.pojo.Users;

public interface UsersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Long id);

    Users selectByUsername(String username);
    Users selectByEmail(String email);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);
}