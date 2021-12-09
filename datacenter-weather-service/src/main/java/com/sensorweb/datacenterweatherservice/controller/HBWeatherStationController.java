package com.sensorweb.datacenterweatherservice.controller;

import com.sensorweb.datacenterweatherservice.entity.HBWeatherStation;
import com.sensorweb.datacenterweatherservice.service.HBWeatherStationService;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

public class HBWeatherStationController {
    @Autowired
    HBWeatherStationService hbWeatherStationService;
    @Autowired
    HBWeatherStation hbWeatherStation;
    @GetMapping(value = "insertHBWeatherStation")
    public void insertHBWeatherStation(@ApiParam(name = "time", value = "当前页码") @Param("time") String time)
    {

        try {
            String document = hbWeatherStationService.getApiDocument(time);
            boolean res = hbWeatherStationService.getIOTInfo(document);
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
