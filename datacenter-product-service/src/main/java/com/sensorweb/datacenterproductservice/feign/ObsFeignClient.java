package com.sensorweb.datacenterproductservice.feign;

import com.sensorweb.datacenterproductservice.entity.Observation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.List;

@Service
@FeignClient(value = "sos-obs-service")
public interface ObsFeignClient {

    @DeleteMapping("delete/{id}")
    int deleteObservationById(@PathVariable("id") String procedureId);

    @GetMapping(path = "getObservationByConditions")
    List<Observation> getObservationByConditions(@RequestParam("dataType") String dataType, @RequestParam("ranSpa") String ranSpa,
                                                 @RequestParam("timeBegin") Instant timeBegin, @RequestParam("timeEnd") Instant timeEnd);

    @GetMapping(path = "getObservationByNewConditions")
    List<Observation> getObservationByNewConditions(@RequestParam("dataType") String dataType, @RequestParam("ranSpa") String ranSpa,
                                                 @RequestParam("timeBegin") Instant timeBegin, @RequestParam("timeEnd") Instant timeEnd);

    @GetMapping(path = "getObservationByTimeAndSpa")
    List<Observation>getObservationByTimeAndSpa( @RequestParam("ranSpa") String ranSpa,@RequestParam("timeBegin") Instant timeBegin, @RequestParam("timeEnd") Instant timeEnd);


}
