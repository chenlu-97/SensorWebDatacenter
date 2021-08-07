package com.sensorweb.sosobsservice.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;



@FeignClient(value = "datacenter-offline-service")
public interface OfflineFeignClient {
    @GetMapping(path = "getGFNumber")
    int getGFNum();


    @GetMapping(path = "getWaterPollutionNumber")
    int getWaterPollutionNum();


    @GetMapping(path = "getWaterQualityNumber")
    int getWaterQualityNum();

}
