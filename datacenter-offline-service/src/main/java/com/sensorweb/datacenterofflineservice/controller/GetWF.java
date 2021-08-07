package com.sensorweb.datacenterofflineservice.controller;


import com.sensorweb.datacenterofflineservice.service.GetWFService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin
public class GetWF {
    @Autowired
    GetWFService getWeatherForecastService;

    @ApiOperation("启动天气预测数据的离线数据的入库")
    @PostMapping(value = "WFdata2DB")
    @ResponseBody
    public String WFdata2DB() throws Exception {
        int status = getWeatherForecastService.insertWeatherForecast();
        String res ;
        if (status > 0) {
            res = "SUCESS!!!";
        } else {
            res = "failed!!!";
        }
        return res;
    }



}
