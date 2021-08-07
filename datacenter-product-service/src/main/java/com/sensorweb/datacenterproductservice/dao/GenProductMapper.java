package com.sensorweb.datacenterproductservice.dao;

import com.sensorweb.datacenterproductservice.entity.GenProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GenProductMapper {
    int insertData(GenProduct genProduct);
    GenProduct selectById(int id);
    GenProduct selectByType(String type);
    GenProduct selectByTypeAndRegion(@Param("type") String type, @Param("region") String region);
    List<GenProduct> selectAll();
}
