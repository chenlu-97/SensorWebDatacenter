package com.sensorweb.datacenterproductservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@FeignClient(value = "datacenter-weather-service")
public interface WeatherFeignClient {

    @GetMapping(path = "getExportWeatherDataByIds")
    Map<String, String> getExportWeatherDataByIds(@RequestParam(value = "ids") List<String> ids, @RequestParam(value = "time") Instant time , @RequestParam(value = "geotype") String geotype);
}
