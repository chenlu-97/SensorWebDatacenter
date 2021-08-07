package com.sensorweb.datacenterproductservice.dao;

import com.sensorweb.datacenterproductservice.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProductMapper {

    int deleteByPrimaryKey(String productId);

    int insert(Product record);

    int insertSelective(Product record);

    List<Product> selectByServiceAndTime(@Param("serviceName") String serviceName, @Param("manufactureDate") String manufactureDate);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectByattribute(@Param("productName") String name, @Param("timeResolution") String time, @Param("spatialResolution") String spatial, @Param("dimension") String dim);

    List<Product> selectAllproduct();


    boolean insertData(Product product);

    List<Product> selectAll();
}