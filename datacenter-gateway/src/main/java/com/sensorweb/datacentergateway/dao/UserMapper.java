package com.sensorweb.datacentergateway.dao;

import com.sensorweb.datacentergateway.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    int insertData(User user);

    User selectByUsername(String username);
}
