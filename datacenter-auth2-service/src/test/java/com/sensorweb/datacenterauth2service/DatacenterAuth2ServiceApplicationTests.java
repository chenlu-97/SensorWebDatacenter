package com.sensorweb.datacenterauth2service;

import com.sensorweb.datacenterauth2service.dao.UserMapper;
import com.sensorweb.datacenterauth2service.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class DatacenterAuth2ServiceApplicationTests {

    @Test
    void contextLoads() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String ss = bCryptPasswordEncoder.encode("12345");
        System.out.println(ss);
    }

    @Autowired
    private UserMapper userMapper;
    @Test
    public void test() {
        User user = userMapper.selectByUsername("sensorweb");
        System.out.println(user);
    }

}
