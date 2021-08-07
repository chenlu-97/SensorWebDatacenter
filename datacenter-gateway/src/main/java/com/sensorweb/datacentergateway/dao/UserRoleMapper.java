package com.sensorweb.datacentergateway.dao;

import com.sensorweb.datacentergateway.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserRoleMapper {
    int insertData(UserRole userRole);

    List<UserRole> selectByUid(int uid);
}
