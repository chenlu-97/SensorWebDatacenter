package com.sensorweb.datacenterairservice.dao;

import com.sensorweb.datacenterairservice.entity.TWEPA;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
@Mapper
public interface TWEPAMapper {
    int insertData(TWEPA twepa);

    int insertDataBatch(List<TWEPA> twepas);

    TWEPA selectByIdAndTime(@Param("uniquecode") String uniquecode, @Param("time")Instant time);

    List<TWEPA> selectByTemporal(@Param("begin") Instant begin, @Param("end") Instant end);

    int selectByTime(@Param("begin") Instant begin, @Param("end") Instant end);

    int selectNum();

    List<TWEPA> selectByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    List<TWEPA> selectByIds(@Param("siteid") List<String> siteid,@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    List<TWEPA> selectByIdAndTimeNew(@Param("type") String type , @Param("spa") String spa, @Param("time")Instant time);

    int updateTWData(TWEPA twepa);
}
