package com.sensorweb.sossensorservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "sos-obs-service")
public interface ObsFeignClient {

    @DeleteMapping("delete/{id}")
    int deleteObservationById(@PathVariable("id") String procedureId);

}
