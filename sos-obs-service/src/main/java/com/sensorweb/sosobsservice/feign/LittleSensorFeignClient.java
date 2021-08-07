package com.sensorweb.sosobsservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "datacenter-littleSensor-service")
public interface LittleSensorFeignClient {

    @GetMapping(path = "getLittleSensorNumber")
    int getLittleSensorNumber();
}
