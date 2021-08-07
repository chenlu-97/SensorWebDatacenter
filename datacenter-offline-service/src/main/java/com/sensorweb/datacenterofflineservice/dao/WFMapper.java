package com.sensorweb.datacenterofflineservice.dao;


import com.sensorweb.datacenterofflineservice.entity.WF;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface WFMapper {

     int insertWF(WF weatherforecast);
}
