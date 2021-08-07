package com.sensorweb.sossensorservice.dao;

import com.sensorweb.sossensorservice.entity.Characteristic;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CharacteristicMapper {
    int insertData(Characteristic characteristic);

    int deleteById(int id);
    int deleteByProcedureId(String procedureId);

    List<Characteristic> selectByProcedureId(String procedureId);
    List<Characteristic> selectByName(String name);
}
