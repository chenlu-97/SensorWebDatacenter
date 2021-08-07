package com.sensorweb.datacenterofflineservice.dao;

import com.sensorweb.datacenterofflineservice.entity.WH_WaterQuality;
import com.sensorweb.datacenterofflineservice.entity.WH_WaterStation;
import com.sensorweb.datacenterofflineservice.entity.WaterPollution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
@Mapper
public interface WH_WaterQualityMapper {


    int insertHB_WaterQuality(WH_WaterQuality wh_waterQuality);
    int insertDataBatch(List<WH_WaterQuality> wh_waterQuality);

    int insertWH_WaterQuality_AutoStation(WH_WaterQuality wh_waterQuality);

    int insertDataBatch_AutoStation(List<WH_WaterQuality> wh_waterQuality);


    List<WH_WaterQuality> selectById(@Param(value = "ids") List<Integer> ids);

    List<WH_WaterQuality> selectWH_WaterQuality();
    List<WH_WaterQuality> selectWH_WaterQuality_AutoStation();

    List<WH_WaterQuality> selectByAttribute(@Param(value = "sectionName") String sectionName);

    List<WH_WaterQuality> selectByAttribute_AutoStation(@Param(value = "sectionName") String sectionName);

    int selectNum1();

    int selectNum2();


    List<WH_WaterQuality> selectByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    List<WH_WaterQuality> selectByIds(@Param("sectionname") List<String> sectionname,@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);


    List<WH_WaterQuality> selectAutoByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    List<WH_WaterQuality> selectAutoByIds(@Param("sectionname") List<String> sectionname,@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
}
