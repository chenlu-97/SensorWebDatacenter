package com.sensorweb.sosobsservice.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "datacenter-laads-service")
public interface LaadsFeignClient {

    @GetMapping(path = "getModisNumber")
    int getModisNum();
}
