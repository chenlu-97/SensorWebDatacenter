package com.sensorweb.datacenterairservice.dao;

import com.sensorweb.datacenterairservice.entity.AirQualityHour;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Repository
@Mapper
public interface AirQualityHourMapper {


    int insertData(AirQualityHour airQualityHour);

    int insertDataBatch(List<AirQualityHour> airQualityHours);

    List<AirQualityHour> selectByIds(@Param("uniquecode") List<String> uniquecode,@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    List<AirQualityHour> selectAll();

    List<AirQualityHour> selectByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    int selectNum();

    int selectNumberByIds(List<String> uniquecode);

    List<AirQualityHour> selectByTemporal(@Param("begin") Instant begin, @Param("end") Instant end);

    int selectByTime(@Param("begin") Instant begin, @Param("end") Instant end);

    AirQualityHour selectByIdAndTime(@Param("uniquecode")String uniquecode, @Param("time")Instant time);

    List<AirQualityHour> selectByIdAndTimeNew(@Param("type") String type , @Param("spa") String spa, @Param("time")Instant time);

    List<AirQualityHour> getDataByNameAndTime(@Param("name")String name,@Param("time")Instant time);
}
