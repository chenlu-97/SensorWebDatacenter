package com.sensorweb.datacenterproductservice;

import com.sensorweb.datacenterproductservice.dao.DataPathMapper;
import com.sensorweb.datacenterproductservice.service.GetGenProductService;
import com.sensorweb.datacenterproductservice.service.InsertGenProductService;
import com.sensorweb.datacenterproductservice.service.InsertProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

@SpringBootTest
class DatacenterInsertProductServiceApplicationTests {
    @Autowired
    InsertGenProductService insertGenProductService;
    @Autowired
    GetGenProductService getGenProductService;
    @Test
    void contextLoads() {
//        insertGenProductService.insertDataPathByHour();
//        Instant begin = null;
//        Instant end = null;
//        String type = "Himawari";
//        String spa = "全国" ;
//        System.out.println(getGenProductService.selectFilePathByTimeAndType(begin,end,type,spa));
    }

}
