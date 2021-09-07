package com.sensorweb.datacenterweatherservice;

import com.sensorweb.datacenterweatherservice.entity.ChinaWeather;
import com.sensorweb.datacenterweatherservice.service.InsertWeatherInfo;
import com.sensorweb.datacenterweatherservice.service.TianXingZhouService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@SpringBootTest
class DatacenterWeatherServiceApplicationTests {

//    @Autowired
//    private InsertWeatherInfo insertWeatherInfo;

    @Autowired
    private TianXingZhouService tianXingZhouService;

    @Test
    void contextLoads() throws IOException, ParseException {
//        List<ChinaWeather> chinaWeathers = insertWeatherInfo.getWeatherInfo();
//        System.out.println("!!!");

//        boolean flag = insertWeatherInfo.insertWeatherInfoBatch(insertWeatherInfo.getWeatherInfo());
//        System.out.println("flag = " + flag);
        boolean test =  tianXingZhouService.getIOTInfo();
        System.out.println("test = " + test);
    }

}
