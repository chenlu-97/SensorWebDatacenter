package com.sensorweb.datacenterofflineservice.controller;


import com.sensorweb.datacenterofflineservice.entity.WH_WaterStation;
import com.sensorweb.datacenterofflineservice.entity.WaterPollutionStation;
import com.sensorweb.datacenterofflineservice.service.GetWaterPollutionStationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
public class GetWaterPollutionStation {
    @Autowired
    GetWaterPollutionStationService getWaterPollutionStationService;


    @PostMapping(value = "registryWaterPollutionStation")
    public Map<String, String> registryWaterPollutionStation(@RequestParam("file") MultipartFile file) {
//        String tmpDir = "/data/Ai-Sensing/DataCenter/water/pollution_data/";
        String tmpDir = "C:/Users/chenlu/Desktop/省站资料/省站资料（修改）/pollution_data/";
        Map<String, String> res = new HashMap<>();
        try {
            if (file!=null) {
                String fileName = file.getOriginalFilename();
                File excel = new File(tmpDir + fileName);
                file.transferTo(excel);
                List<WaterPollutionStation> waterPollutionStation = getWaterPollutionStationService.getStationInfoByExcel(excel);
                boolean flag1 = getWaterPollutionStationService.insertStationInfoBatch(waterPollutionStation);
                boolean flag2 = getWaterPollutionStationService.insertStationInfoBatchInStation(waterPollutionStation);
                if (flag1&&flag2) {
                    res.put("status", "success");
                } else {
                    res.put("status", "failed");
                }
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            res.put("status", "failed");
        }
        return res;
    }

}
