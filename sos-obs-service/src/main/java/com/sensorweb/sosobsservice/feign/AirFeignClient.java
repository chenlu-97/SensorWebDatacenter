package com.sensorweb.sosobsservice.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;


@FeignClient(value = "datacenter-air-service")
public interface AirFeignClient {

    @GetMapping(path = "getHBAirNumber")
    int getHBAirNum();

    @GetMapping(path = "getCHAirNumber")
    int getCHAirNum();

    @GetMapping(path = "getTWAirNumber")
    int getTWAirNum();

    @GetMapping(path = "getAirCountOfHour")
    Integer getAirCountOfHour(@RequestParam(value = "start") Instant start);


}
