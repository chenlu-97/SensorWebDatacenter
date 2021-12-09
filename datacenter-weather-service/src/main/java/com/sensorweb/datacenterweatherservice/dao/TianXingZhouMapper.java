package com.sensorweb.datacenterweatherservice.dao;

import com.sensorweb.datacenterweatherservice.entity.TianXingZhouStation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
@Repository
@Mapper
public interface TianXingZhouMapper {

    int insertData(TianXingZhouStation tianXingZhouStation);

    List<TianXingZhouStation> selectByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    int selectNum();

    List<TianXingZhouStation> selectByTime(@Param("time") Instant time);
}
