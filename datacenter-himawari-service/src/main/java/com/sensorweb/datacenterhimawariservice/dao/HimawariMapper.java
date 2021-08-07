package com.sensorweb.datacenterhimawariservice.dao;

import com.sensorweb.datacenterhimawariservice.entity.Himawari;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
@Mapper
public interface HimawariMapper {
    int insertData(Himawari himawari);
    int insertDataBatch(List<Himawari> himawaris);

    List<Himawari> selectByTime(@Param("time") Instant time);

    List<Himawari> selectMaxTimeData();


    Himawari selectByName(String name);

    int selectNum();

    List<Himawari> selectByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    List<Himawari> selectByIds(@Param("uniquecode") List<String> uniquecode,@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

//    Himawari selectBySpatialAndTemporal(@Param("region") String region, @Param("begin") Instant begin, @Param("end") Instant end);
}
