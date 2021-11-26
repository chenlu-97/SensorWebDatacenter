package com.sensorweb.datacenterweatherservice.dao;

import com.sensorweb.datacenterweatherservice.entity.HBWeatherStation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface HBWeatherStationMapper {
    int insertData(HBWeatherStation hbWeatherStation);
}
