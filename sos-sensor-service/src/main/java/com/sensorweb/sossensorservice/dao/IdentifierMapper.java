package com.sensorweb.sossensorservice.dao;

import com.sensorweb.sossensorservice.entity.Identifier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IdentifierMapper {
    int insertData(Identifier identifier);

    int deleteById(int id);
    int deleteByProcedureId(String procedureId);

    List<Identifier> selectByProcedureId(String procedureId);
    Identifier selectByLabelAndProcedureId(@Param("label") String label, @Param("procedureId") String procedureId);
    List<Identifier> selectByLabelAndValue(@Param("label") String label, @Param("value") String value);
}
