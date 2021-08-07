package com.sensorweb.sossensorservice.dao;

import com.sensorweb.sossensorservice.entity.ValidTime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
@Mapper
public interface ValidTimeMapper {
    int insertData(ValidTime validTime);

    int deleteById(int id);
    int deleteByProcedureId(String procedureId);

    ValidTime selectByProcedureId(String procedureId);
    List<ValidTime> selectByTime(@Param("begin") Instant beginTime, @Param("end") Instant endTime);
}
