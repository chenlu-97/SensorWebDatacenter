package com.sensorweb.datacenterairservice.dao;

import com.sensorweb.datacenterairservice.entity.ChinaAirQualityHour;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
@Mapper
public interface ChinaAirQualityHourMapper {


    int insert(ChinaAirQualityHour record);

    List<ChinaAirQualityHour> selectByIdAndTime(@Param("stationcode") List<String> stationcode, @Param("time")Long time);

    int selectByTime(@Param("begin") Long begin, @Param("end") Long end);

    int selectNum();

    List<ChinaAirQualityHour> selectByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    List<ChinaAirQualityHour> selectByIds(@Param("stationcode") List<String> stationcode,@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    List<ChinaAirQualityHour> selectByIdAndTimeNew(@Param("type") String type , @Param("spa") String spa, @Param("time") Long time);
}