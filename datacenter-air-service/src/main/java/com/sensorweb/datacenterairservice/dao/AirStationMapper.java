package com.sensorweb.datacenterairservice.dao;

import com.sensorweb.datacenterairservice.entity.AirStationModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AirStationMapper {

    int insertData(AirStationModel airStationModel);
    int insertDataBatch(List<AirStationModel> airStationModels);

    AirStationModel selectById(int id);
    List<AirStationModel> selectByStationId(String stationId);
    int selectAllCount();
    List<AirStationModel> selectByOffsetAndLimit(@Param("offset") int offset, @Param("limit") int limit);
    List<AirStationModel> selectByAll();

    List<String> selectStationByTypeAndSpa(@Param("type") String type , @Param("spa") String spa);

    boolean updateGeom(@Param("wkt") String wkt,@Param("lon") double lon,@Param("lat") double lat);


    List<String> selectStation(@Param("stype")String stype);
}
