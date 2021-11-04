package com.sensorweb.datacentermobileservice.dao;


import com.sensorweb.datacentermobileservice.entity.MeasuringVehicle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MeasuringVehicleMapper {
    int insertData(MeasuringVehicle measuringVehicle);

    List<MeasuringVehicle> selectByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    int selectNum();

}
