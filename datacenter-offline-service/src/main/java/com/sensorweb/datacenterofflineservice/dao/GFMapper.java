package com.sensorweb.datacenterofflineservice.dao;

import com.sensorweb.datacenterofflineservice.entity.GF;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GFMapper {

    int insertGF(GF gf);

    List<GF> selectByIds(@Param(value = "ids") List<Integer> ids);

    List<GF> selectGF();

    List<GF> selectGFByAttribute(@Param(value = "satelliteID") String satelliteID, @Param(value = "season") String season);

//    List<GF> selectGFBySatellite(@Param(value = "satelliteID") String satelliteID);

    int selectNum();

    List<GF> selectByName(@Param("satelliteId") String satelliteId,@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    List<GF> selectByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
}