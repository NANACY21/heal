package com.personal.service;

import com.personal.pojo.NUser;

import java.util.List;

public interface NUserService {
    public int saveNUser(NUser nUser);
    int delNUserByUID(long uID);
    int updateNUser(NUser nUser);
    public NUser getNUserByUID(long uID);
    List<NUser> getAllNUser();
}
