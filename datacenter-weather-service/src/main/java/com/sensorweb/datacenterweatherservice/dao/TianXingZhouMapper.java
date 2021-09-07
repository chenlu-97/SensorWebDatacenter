package com.sensorweb.datacenterweatherservice.dao;

import com.sensorweb.datacenterweatherservice.entity.ChinaWeather;
import com.sensorweb.datacenterweatherservice.entity.TianXingZhouStation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface TianXingZhouMapper {

    int insertData(TianXingZhouStation tianXingZhouStation);

    List<TianXingZhouStation> selectByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

}
