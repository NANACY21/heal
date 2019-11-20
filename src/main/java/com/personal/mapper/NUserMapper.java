package com.personal.mapper;

import com.personal.pojo.NUser;

import java.util.List;

public interface NUserMapper {
    int insertNUser(NUser nUser);
    int deleteNUserByUID(long uID);
    int updateNUser(NUser nUser);
    NUser selectNUserByUID(long uID);
    List<NUser> selectAllNUser();
}
