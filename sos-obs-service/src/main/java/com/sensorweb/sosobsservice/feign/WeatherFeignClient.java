package com.sensorweb.sosobsservice.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;

@FeignClient(value = "datacenter-weather-service")
public interface WeatherFeignClient {

    @GetMapping(path = "getWeatherNumber")
    int getWeatherNum();

    @GetMapping(path = "getWeatherCountOfHour")
    Integer getWeatherCountOfHour(@RequestParam(value = "start") Instant start);



}
