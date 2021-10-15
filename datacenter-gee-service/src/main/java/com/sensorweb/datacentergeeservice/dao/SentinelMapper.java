package com.sensorweb.datacentergeeservice.dao;
import com.sensorweb.datacentergeeservice.entity.Sentinel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SentinelMapper {


    List<Sentinel> selectByattribute(@Param("spacecraftID") String spacecraftID, @Param("Date") String Date, @Param("Cloudcover") String Cloudcover, @Param("imageType") String imageType);

    List<Sentinel> selectAll();

    List<Sentinel> selectByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    int selectNum();

    List<Sentinel> selectByIds(@Param("imageid") List<String> imageid,@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
}
