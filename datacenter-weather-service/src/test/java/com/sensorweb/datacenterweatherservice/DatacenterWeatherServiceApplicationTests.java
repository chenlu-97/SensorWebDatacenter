package com.sensorweb.datacenterweatherservice;

import com.sensorweb.datacenterweatherservice.entity.ChinaWeather;
import com.sensorweb.datacenterweatherservice.service.InsertWeatherInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@SpringBootTest
class DatacenterWeatherServiceApplicationTests {

    @Autowired
    private InsertWeatherInfo insertWeatherInfo;

    @Test
    void contextLoads() throws IOException, ParseException {
        List<ChinaWeather> chinaWeathers = insertWeatherInfo.getWeatherInfo();
        System.out.println("!!!");
    }

}
