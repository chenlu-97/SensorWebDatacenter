package com.sensorweb.sossensorservice.dao;

import com.sensorweb.sossensorservice.entity.Capability;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CapabilityMapper {
    int insertData(Capability capability);

    int deleteById(int id);
    int deleteByProcedureId(String procedureId);

    List<Capability> selectByProcedureId(String procedureId);
    List<Capability> selectByName(String name);
}
