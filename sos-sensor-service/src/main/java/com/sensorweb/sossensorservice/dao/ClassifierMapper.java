package com.sensorweb.sossensorservice.dao;

import com.sensorweb.sossensorservice.entity.Classifier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ClassifierMapper {
    int insertData(Classifier classifier);

    int deleteById(int id);
    int deleteByProcedureId(String identifier);

    List<Classifier> selectByProcedureId(String procedureId);
    List<Classifier> selectByLabelAndValue(@Param("label") String label, @Param("value") String value);
    Classifier selectByLabelAndProcedureId(@Param("label") String label, @Param("procedureId") String procedureId);
}
