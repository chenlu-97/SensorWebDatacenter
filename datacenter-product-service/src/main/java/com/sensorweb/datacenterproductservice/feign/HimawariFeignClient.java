package com.sensorweb.datacenterproductservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.Map;

@Service
@FeignClient(value = "datacenter-himawari-service")
public interface HimawariFeignClient {
    @GetMapping("getHimawariDataById")
    String getHimawariDataById(@RequestParam("time")Instant time);

    @GetMapping("getHimawariMaxTimeData")
    String getHimawariMaxTimeData();

    @GetMapping(value = "getHimawariData")
    Map<String, String> getHimawariData(@RequestParam("datetime")String datetime);

    }
