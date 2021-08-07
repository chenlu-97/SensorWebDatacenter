package com.sensorweb.sossensorservice.dao;

import com.sensorweb.sossensorservice.entity.Text;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TextMapper {
    int insertData(Text text);

    int deleteById(int id);
    int deleteByOutId(String outId);

    List<Text> selectByOutId(String outId);
    List<String> selectByNameAndValue(@Param("name") String name, @Param("value") String value);
}
