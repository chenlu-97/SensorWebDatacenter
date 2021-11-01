package com.sensorweb.datacenterweatherservice;

import com.alibaba.fastjson.JSONObject;
import com.sensorweb.datacenterweatherservice.entity.ChinaWeather;
import com.sensorweb.datacenterweatherservice.service.InsertFY4AService;
import com.sensorweb.datacenterweatherservice.service.InsertWeatherInfo;
import com.sensorweb.datacenterweatherservice.service.TianXingZhouService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class DatacenterWeatherServiceApplicationTests {

//    @Autowired
//    private InsertWeatherInfo insertWeatherInfo;


//    private TianXingZhouService tianXingZhouService;
    @Autowired
    private InsertFY4AService insertFY4AService;

//    void contextLoads() throws IOException, ParseException {
////        List<ChinaWeather> chinaWeathers = insertWeatherInfo.getWeatherInfo();
////        System.out.println("!!!");
//
////        boolean flag = insertWeatherInfo.insertWeatherInfoBatch(insertWeatherInfo.getWeatherInfo());
////        System.out.println("flag = " + flag);
//        boolean test =  tianXingZhouService.getIOTInfo();
//        System.out.println("test = " + test);
//    }
    @Test
    void testService() throws  IOException, ParseException{
        String res = insertFY4AService.getApiDocument();
        insertFY4AService.getIOTInfo(res);
    }

}
