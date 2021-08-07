package com.sensorweb.datacenterweatherservice.dao;

import com.sensorweb.datacenterweatherservice.entity.WeatherStationModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface WeatherStationMapper {
    int insertData(WeatherStationModel weatherStationModel);
    int insertDataBatch(List<WeatherStationModel> weatherStationModels);

    WeatherStationModel selectById(int id);
    WeatherStationModel selectByStationId(String stationId);
    int selectAllCount();
    List<WeatherStationModel> selectByOffsetAndLimit(@Param("offset") int offset, @Param("limit") int limit);
    List<WeatherStationModel> selectByStationType(String stype);
}
