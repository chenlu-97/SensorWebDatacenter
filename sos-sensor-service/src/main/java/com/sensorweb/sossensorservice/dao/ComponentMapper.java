package com.sensorweb.sossensorservice.dao;

import com.sensorweb.sossensorservice.entity.Component;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ComponentMapper {
    int insertData(Component component);

    int deleteById(int id);
    int deleteByPlatformId(String platformId);
    int deleteByHref(String href);

    Component selectById(int id);
    List<Component> selectByPlatformId(String platformId);
    Component selectByHref(String href);//查询传感器所属平台
}
