package com.sensorweb.datacenterofflineservice.dao;


import com.sensorweb.datacenterofflineservice.entity.WH_WaterQuality;
import com.sensorweb.datacenterofflineservice.entity.WaterPollution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface WaterPollutionMapper {

    int insertWaterPollution(WaterPollution waterPollution);

    int insertDataBatch(List<WaterPollution> waterPollution);


    List<WaterPollution> selectById(@Param(value = "ids") List<Integer> ids);

    List<WaterPollution> selectWaterPollutionStation();

    List<WaterPollution> selectByAttribute(@Param(value = "stationName") String stationName);

    int selectNum();

    List<WaterPollution> selectByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    List<WaterPollution> selectByIds(@Param("stationname") List<String> stationname,@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
}
