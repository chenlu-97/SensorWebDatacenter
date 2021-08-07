package com.sensorweb.sossensorservice.dao;

import com.sensorweb.sossensorservice.entity.Vector;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface VectorMapper {
    int insertData(Vector vector);

    int deleteById(int id);
    int deleteByOutId(String outId);

    List<Vector> selectByOutId(String outId);
    List<String> selectByLowerCorner(@Param("lon") double longitude, @Param("lat") double latitude);
    List<String> selectByUpperCorner(@Param("lon") double longitude, @Param("lat") double latitude);
}
