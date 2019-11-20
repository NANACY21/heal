package com.personal.service.impl;

import com.personal.mapper.NUserMapper;
import com.personal.pojo.NUser;
import com.personal.service.NUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("ALL")
@Service
@Transactional
public class NUserServiceImpl implements NUserService {

    @Autowired
    private NUserMapper mapper;
    @Override
    public int saveNUser(NUser nUser) {
        return mapper.insertNUser(nUser);
    }

    @Override
    public List<NUser> getAllNUser() {
        return mapper.selectAllNUser();
    }

    @Override
    public NUser getNUserByUID(long uID) {
        return mapper.selectNUserByUID(uID);
    }

    @Override
    public int delNUserByUID(long uID) {
        return mapper.deleteNUserByUID(uID);
    }

    @Override
    public int updateNUser(NUser nUser) {
        return mapper.updateNUser(nUser);
    }
}
