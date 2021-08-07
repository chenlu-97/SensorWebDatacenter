package com.sensorweb.datacenterofflineservice.dao;

import com.sensorweb.datacenterofflineservice.entity.WH_WaterStation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface WH_WaterStationMapper {
    int insertWH_WaterStation(WH_WaterStation wh_WaterStation);

    List<WH_WaterStation> selectByIds(@Param(value = "ids") List<Integer> ids);

    List<WH_WaterStation> selectHB_WaterStation();

    List<WH_WaterStation> selectByAttribute(@Param(value = "sectionName") String sectionName);

    int insertDataBatch(List<WH_WaterStation> waterStation);

    //插入到Station集合表中
    int insertDataBatchInStation(List<WH_WaterStation> waterStation);
}
