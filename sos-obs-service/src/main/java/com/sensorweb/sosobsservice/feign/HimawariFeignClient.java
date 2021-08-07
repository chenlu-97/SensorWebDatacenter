package com.sensorweb.sosobsservice.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "datacenter-himawari-service")
public interface HimawariFeignClient {

    @GetMapping(path = "getHimawariNumber")
    int getHimawariNum();
}
