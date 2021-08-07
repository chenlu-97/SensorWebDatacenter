package com.sensorweb.datacenterproductservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@FeignClient(value = "datacenter-air-service")
public interface AirFeignClient {

    @GetMapping(path = "getExportAll_HBAirDataByIds")
    Map<String, String> getExportAll_HBAirDataByIds(@RequestParam(value = "ids") List<String> ids, @RequestParam(value = "time") Instant time,@RequestParam(value = "geotype") String geotype);

    @GetMapping(path = "getExportHBAirDataByIds")
    Map<String, String> getExportHBAirDataByIds(@RequestParam(value = "ids") List<String> ids, @RequestParam(value = "time") Instant time, @RequestParam(value = "geotype") String geotype);

    @GetMapping(path = "getExportTWAirDataByIds")
    void getExportTWAirDataByIds(@RequestParam(value = "ids") List<String> ids, @RequestParam(value = "time") Instant time, @RequestParam(value = "geotype") String geotype);

    @GetMapping(path = "getExportCHAirDataByIds")
    Map<String, String> getExportCHAirDataByIds(@RequestParam(value = "ids") List<String> ids, @RequestParam(value = "time") Long time, @RequestParam(value = "geotype") String geotype);

    @GetMapping(value = "getStationByTypeAndSpa")
    Map<String,List<String>> getStationByTypeAndSpa(@RequestParam(value = "type") String type, @RequestParam(value = "spa") String spa);

    @GetMapping(value = "getStationByTypeAndSpaOfAllHBAir")
    List<String> getStationByTypeAndSpaOfAllHBAir(@RequestParam(value = "type") String type, @RequestParam(value = "spa") String spa);
}

