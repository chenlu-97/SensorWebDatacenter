package com.sensorweb.sosobsservice.dao;

import com.sensorweb.sosobsservice.entity.Observation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
@Mapper
public interface ObservationMapper {
    int insertData(Observation observation);

    int insertDataBatch(List<Observation> observations);

    int deleteByProcedureId(String procedureId);

    int selectNum();

    List<Observation> selectByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
    List<Observation> selectByProcedureId(String procedureId);
    List<Observation> selectByObsType(String type);
    List<Observation> selectByObsProp(String obsProperty);
    List<Observation> selectByObsTime(Instant obsTime);//查询obsTime之前的观测数据
    List<Observation> selectByTemporal(@Param("begin") Instant begin, @Param("end") Instant end);
    List<Observation> selectBySpatial(String bbox);//评断两个集合图形是否有Within关系

    List<Observation> selectByTemAndSpaAndType(@Param("begin") Instant begin, @Param("end") Instant end, @Param("type") String type,
                                               @Param("spa") String spa, @Param("cityIds") List<Integer> cityIds);
    List<Integer> selectCityIdInWuCityCircle();//查询武汉城市圈的所有城市id
    List<Integer> selectCityIdInChina();//查询全国的id
    List<Integer> selectCityIdInChangjiang();//查询长江经济带的所有城市id

    List<Observation> selectByTemAndType(@Param("begin") Instant begin, @Param("end") Instant end, @Param("type") String type);

    List<Observation> selectByTemAndSpaAndTypeNew(@Param("begin") Instant begin, @Param("end") Instant end, @Param("type") String type,
                                                  @Param("spa") String spa);

    boolean selectWHSpa(@Param("wkt") String wkt);
    boolean selectCHSpa(@Param("wkt") String wkt);
    boolean selectCJSpa(@Param("wkt") String wkt);

    List<Observation> selectByTimeAndSpa(@Param("begin") Instant begin, @Param("end") Instant end,@Param("spa") String spa);
}
