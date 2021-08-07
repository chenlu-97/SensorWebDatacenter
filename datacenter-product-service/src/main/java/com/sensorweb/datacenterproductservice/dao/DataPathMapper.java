package com.sensorweb.datacenterproductservice.dao;


import com.sensorweb.datacenterproductservice.entity.DataPath;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
@Mapper
public interface DataPathMapper {

    boolean insertNeededData(DataPath dataPath);

    String selectFilePathByTimeAndType(@Param("begin") Instant begin, @Param("type") String type, @Param("spa") String spa);
}
