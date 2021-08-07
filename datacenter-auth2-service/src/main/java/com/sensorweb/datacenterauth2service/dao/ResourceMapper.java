package com.sensorweb.datacenterauth2service.dao;

import com.sensorweb.datacenterauth2service.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ResourceMapper {
    int insertData(Resource resource);

    Resource selectById(int id);
    List<Resource> selectByPath(String path);
}
