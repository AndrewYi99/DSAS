package com.dsas.service.impl;

import com.dsas.model.dao.UserMapper;
import com.dsas.model.pojo.User;
import com.dsas.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    @Resource
    UserMapper userMapper;
    @Test
    void register() {
       // User test = userMapper.selectByName("test");
        userMapper.selectByPrimaryKey(1);
    //System.out.println(test);
    }
}