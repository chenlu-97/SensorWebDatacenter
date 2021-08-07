package com.sensorweb.sossensorservice.dao;

import com.sensorweb.sossensorservice.entity.Procedure;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProcedureMapper {
    int insertData(Procedure procedure);

    int deleteById(String id);

    Procedure selectById(String id);
    List<String> selectAllProcedureIds();
    List<String> selectAllSensorIds();
    List<String> selectAllPlatformIds();

    boolean isExist(String procedureId);

    Procedure selectById123(String id);
}
