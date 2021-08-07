package com.sensorweb.datacenterauth2service.dao;

import com.sensorweb.datacenterauth2service.entity.ResRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ResRoleMapper {
    int insertData(ResRole resRole);

    List<ResRole> selectByRid(int rid);
    List<ResRole> selectByResId(int resId);
}
