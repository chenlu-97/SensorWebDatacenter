package com.sensorweb.datacentermobileservice.dao;


import com.sensorweb.datacentermobileservice.entity.MeasuringVehicle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
@Mapper
public interface MeasuringVehicleMapper {



    int insertVocsData(MeasuringVehicle measuringVehicle);

    int insertAirData(MeasuringVehicle measuringVehicle);

    int insertPMData(MeasuringVehicle measuringVehicle);

    int insertSPMSData(MeasuringVehicle measuringVehicle);

    int insertHTData(MeasuringVehicle measuringVehicle);

//    List<MeasuringVehicle> selectByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    int selectNum();

    int insertRadioData(MeasuringVehicle measuringVehicle);

    List<MeasuringVehicle> selectByTime(@Param("begin")Instant begin, @Param("end") Instant end);



    List<MeasuringVehicle> getVocsByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    List<MeasuringVehicle> getHTByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    List<MeasuringVehicle> getPMByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    List<MeasuringVehicle> getAirByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    List<MeasuringVehicle> getSPMSByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
}
