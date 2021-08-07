package com.sensorweb.datacenterofflineservice.dao;


import com.sensorweb.datacenterofflineservice.entity.WH_WaterQuality;
import com.sensorweb.datacenterofflineservice.entity.WaterPollutionStation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface WaterPollutionStationMapper {

    int insertWaterPollutionStation(WaterPollutionStation waterPollutionStation);

    int insertDataBatch(List<WaterPollutionStation> waterPollutionStation);

    int insertDataBatchInStation(List<WaterPollutionStation> waterPollutionStation);


    List<WaterPollutionStation> selectByIds(@Param(value = "ids") List<Integer> ids);

    List<WaterPollutionStation> selectWaterPollutionStation();

    List<WaterPollutionStation> selectByAttribute(@Param(value = "stationName") String stationName);


}
