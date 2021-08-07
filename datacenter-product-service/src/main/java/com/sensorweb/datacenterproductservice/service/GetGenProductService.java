package com.sensorweb.datacenterproductservice.service;

import com.sensorweb.datacenterproductservice.dao.DataPathMapper;
import com.sensorweb.datacenterproductservice.dao.GenProductMapper;
import com.sensorweb.datacenterproductservice.entity.DataPath;
import com.sensorweb.datacenterproductservice.entity.GenProduct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class GetGenProductService {

    @Autowired
    private GenProductMapper genProductMapper;
    @Autowired
    private DataPathMapper dataPathMapper;

    public GenProduct getGenProductById(int id) {
        return genProductMapper.selectById(id);
    }

    public GenProduct getGenProductByType(String type) {
        return genProductMapper.selectByType(type);
    }

    public GenProduct getGenProductByTypeAndRegion(String type, String region) {
//        if (StringUtils.isBlank(region)) {
//            return genProductMapper.selectByType(type);
//        }
        return genProductMapper.selectByTypeAndRegion(type, region);
    }

    public List<GenProduct> getGenProducts() {
        return genProductMapper.selectAll();
    }


    public String  selectFilePathByTimeAndType(Instant begin, String type, String spa){
       return dataPathMapper.selectFilePathByTimeAndType(begin,type,spa);
    }

}
