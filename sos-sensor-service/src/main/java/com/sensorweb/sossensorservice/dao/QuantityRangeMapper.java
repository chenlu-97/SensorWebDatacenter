package com.sensorweb.sossensorservice.dao;

import com.sensorweb.sossensorservice.entity.QuantityRange;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QuantityRangeMapper {
    int insertData(QuantityRange quantityRange);

    int deleteById(int id);
    int deleteByOutId(String outId);

    List<QuantityRange> selectByOutId(String outId);
    List<String> selectByNameAndValue(@Param("name") String value, @Param("minValue") double minValue, @Param("maxValue") double maxValue);
}
