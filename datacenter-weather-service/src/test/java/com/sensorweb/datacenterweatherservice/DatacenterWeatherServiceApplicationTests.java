package com.sensorweb.datacenterweatherservice;

import com.alibaba.fastjson.JSONObject;
import com.sensorweb.datacenterweatherservice.entity.ChinaWeather;
import com.sensorweb.datacenterweatherservice.service.InsertFY4AService;
import com.sensorweb.datacenterweatherservice.service.InsertWeatherInfo;
import com.sensorweb.datacenterweatherservice.service.MeasuringVehicleService;
import com.sensorweb.datacenterweatherservice.service.TianXingZhouService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
class DatacenterWeatherServiceApplicationTests {

//    @Autowired
//    private InsertWeatherInfo insertWeatherInfo;

//<<<<<<< HEAD
//=======
//    @Autowired
//    private TianXingZhouService tianXingZhouService;s
//    @Autowired
//    private MeasuringVehicleService measuringVehicleService;
////>>>>>>> 52d911d9109b97e06808850360aaebef01306b7e

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
//    void contextLoads() throws IOException, ParseException {
//        List<ChinaWeather> chinaWeathers = insertWeatherInfo.getWeatherInfo();
//        System.out.println("!!!");

//        boolean flag = insertWeatherInfo.insertWeatherInfoBatch(insertWeatherInfo.getWeatherInfo());
//        System.out.println("flag = " + flag);
//        boolean test =  tianXingZhouService.getIOTInfo();
//        System.out.println("test = " + test);
//        String data1 = "##0349QN=20211021095827821;ST=22;CN=2011;PW=123456;MN=YD4210001;Flag=4;CP=&&DataTime=20211021095827;a01001-Rtd=13.9,a01001-Flag=N;a01002-Rtd=65.8,a01002-Flag=N;a010061-Rtd=1.03,a010061-Flag=N;a01007-Rtd=0.3,a01007-Flag=N;a01008-Rtd=92.6,a01008-Flag=N;a80001-Rtd=39.36,a80001-Flag=N;a81001-Rtd=30.4614278,a81001-Flag=N;a81002-Rtd=114.392925,a81002-Flag=N&&8801";
//        Matcher m = Pattern.compile("(?<==).+?(?=,)").matcher(data1);
//        while (m.find()) {
//            String result = m.group().trim();
//            System.out.println("result = " + result);
//        }


//       boolean i =  measuringVehicleService.insertData("##0349QN=20211021095827821;ST=22;CN=2011;PW=123456;MN=YD4210001;Flag=4;CP=&&DataTime=20211021095827;a01001-Rtd=13.9,a01001-Flag=N;a01002-Rtd=65.8,a01002-Flag=N;a010061-Rtd=1.03,a010061-Flag=N;a01007-Rtd=0.3,a01007-Flag=N;a01008-Rtd=92.6,a01008-Flag=N;a80001-Rtd=39.36,a80001-Flag=N;a81001-Rtd=30.4614278,a81001-Flag=N;a81002-Rtd=114.392925,a81002-Flag=N&&8801");
//        System.out.println("i = " + i);
//>>>>>>> 52d911d9109b97e06808850360aaebef01306b7e
    }

    }