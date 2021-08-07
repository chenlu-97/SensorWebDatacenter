package com.sensorweb.datacenterauth2service.dao;

import com.sensorweb.datacenterauth2service.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RoleMapper {
    int insertData(Role role);

    Role selectById(int id);
    Role selectByName(String name);
}
