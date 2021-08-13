package com.sensorweb.datacenterproductservice.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@FeignClient(value = "datacenter-offline-service")
public interface OfflineFeignClient {

    @GetMapping(value = "selectGFByImageID")
    String selectGFByImageID(@RequestParam(value = "imageId") String imageId);


    @GetMapping (value = "selectGFByImageIDAndTime")
    Map<String, List<String>> selectGFByImageIDAndTime(@RequestParam(value = "imageId") String imageId, @RequestParam(value = "begin") Instant begin, @RequestParam(value = "end") Instant end);
}
