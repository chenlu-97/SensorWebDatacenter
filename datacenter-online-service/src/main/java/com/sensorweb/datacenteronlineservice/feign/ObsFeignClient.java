package com.sensorweb.datacenteronlineservice.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "sos-obs-service")
public interface ObsFeignClient {

}
