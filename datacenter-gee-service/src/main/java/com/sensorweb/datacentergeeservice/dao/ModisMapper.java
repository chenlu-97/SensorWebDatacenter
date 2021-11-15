package com.sensorweb.datacentergeeservice.dao;


import com.sensorweb.datacentergeeservice.entity.Landsat;
import com.sensorweb.datacentergeeservice.entity.Modis;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ModisMapper {

    List<Modis> selectByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
}
