package com.sensorweb.sosobsservice.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "datacenter-gee-service")
public interface GeeFeignClient {
    @GetMapping(path = "getLandsatNumber")
    int getLandsatNum();
}
