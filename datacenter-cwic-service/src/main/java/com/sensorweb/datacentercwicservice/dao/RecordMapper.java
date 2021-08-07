package com.sensorweb.datacentercwicservice.dao;

import com.sensorweb.datacentercwicservice.entity.Record;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RecordMapper {
    int insertData(Record record);
    int insertDataBatch(List<Record> records);

    Record selectById(int id);
}
