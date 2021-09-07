package com.sensorweb.datacenterweatherservice.dao;

import com.sensorweb.datacenterweatherservice.entity.ChinaWeather;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
@Mapper
public interface WeatherMapper {
    int insertData(ChinaWeather chinaWeather);

    int insertDataBatch(List<ChinaWeather> chinaWeathers);

    ChinaWeather selectById(int id);

    List<ChinaWeather> selectByIdsAndTime(@Param("type") String type , @Param("spa") String spa, @Param("time")Instant time);

    List<ChinaWeather> selectByTemporal(@Param("begin") Instant begin, @Param("end") Instant end);

    List<ChinaWeather> selectByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    int selectByTime(@Param("begin") Instant begin, @Param("end") Instant end);

    int selectNum();

    List<ChinaWeather> selectByIds(@Param("stationid") List<String> stationid,@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    List<ChinaWeather> selectAll();
}
