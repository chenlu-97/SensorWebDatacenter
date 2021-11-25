package com.sensorweb.datacenterlaadsservice.dao;

import com.sensorweb.datacenterlaadsservice.entity.Entry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
@Mapper
public interface EntryMapper {

    List<Entry> getFilePath(@Param("product") String product,@Param("startTime") Instant startTime, @Param("endTime") Instant endTime);

    int insertData(Entry entry);
    int insertDataBatch(List<Entry> entries);

    Entry selectById(int id);

    int selectNum();

    List<Entry> selectByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    List<Entry> selectByIds(@Param("satellite") List<String> satellite,@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    Entry selectNew();
}
