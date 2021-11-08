package com.sensorweb.datacentermobileservice.dao;


import com.sensorweb.datacentermobileservice.entity.SurveyingVessel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
@Mapper
public interface SurveyingVesselMapper {

    int insertData(SurveyingVessel surveyingVessel);

    List<SurveyingVessel> selectByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    int selectNum();

    int insertPosition(SurveyingVessel surveyingVessel);

    int UpdateDataByTime(SurveyingVessel surveyingVessel);
}
