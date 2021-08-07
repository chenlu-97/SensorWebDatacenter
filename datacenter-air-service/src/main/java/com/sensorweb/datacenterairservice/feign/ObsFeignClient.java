package com.sensorweb.datacenterairservice.feign;

import com.sensorweb.datacenterairservice.entity.Observation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "sos-obs-service")
public interface ObsFeignClient {

    @DeleteMapping("delete/{id}")
    void deleteObservationById(@PathVariable("id") String procedureId);

    @PostMapping("insertData")
    int insertData(@RequestBody Observation observation);

    @PostMapping("insertDataBatch")
    int insertDataBatch(@RequestBody List<Observation> observations);


    @PostMapping("insertBeforeSelectWHSpa")
    boolean insertBeforeSelectWHSpa(@RequestBody String wkt);

    @PostMapping("insertBeforeSelectCJSpa")
    boolean insertBeforeSelectCJSpa(@RequestBody String wkt);

    @PostMapping("insertBeforeSelectCHSpa")
    boolean insertBeforeSelectCHSpa(@RequestBody String wkt);
}
