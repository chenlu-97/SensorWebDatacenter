package com.sensorweb.datacenterauth2service.dao;

import com.sensorweb.datacenterauth2service.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    int insertData(User user);

    User selectByUsername(String username);
}
