package com.sensorweb.datacenterairservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(value = "sos-sensor-service")
public interface SensorFeignClient {
    @GetMapping(path = "getAllProcedureInfo")
    Map<String, Object> getAllProcedure();

    @GetMapping(value = "isExist/{id}")
    boolean isExist(@PathVariable("id") String procedureId);
}
