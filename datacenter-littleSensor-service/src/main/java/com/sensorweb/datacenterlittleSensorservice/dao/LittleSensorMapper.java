package com.sensorweb.datacenterlittleSensorservice.dao;

import com.sensorweb.datacenterlittleSensorservice.entity.LittleSensor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface LittleSensorMapper {
    int insertData(LittleSensor entry);
    int insertDataBatch(List<LittleSensor> entries);

    LittleSensor selectById(int id);

    int selectNum();

    List<LittleSensor> selectByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    List<LittleSensor> selectByIds(@Param("deviceid") List<String> deviceid,@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
}
