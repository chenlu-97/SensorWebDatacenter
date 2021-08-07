package com.sensorweb.datacenterweatherservice.controller;

import com.sensorweb.datacenterweatherservice.entity.WeatherStationModel;
import com.sensorweb.datacenterweatherservice.service.InsertStationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@CrossOrigin
@RestController
public class StationController {

    @Value("${datacenter.path.tmpDir}")
    private String tmpDir;

    @Autowired
    private InsertStationInfo insertStationInfo;

    @PostMapping(value = "registryWHWeatherStation")
    public Map<String, String> registryWHWeatherStation(@RequestParam("file") MultipartFile file) {
        Map<String, String> res = new HashMap<>();
        try {
            if (file!=null) {
                String fileName = file.getOriginalFilename();
                File excel = new File(tmpDir + fileName);
                file.transferTo(excel);
                List<WeatherStationModel> weatherStationModels = insertStationInfo.getStationInfoByExcel(excel);
                boolean flag = insertStationInfo.insertStationInfoBatch(weatherStationModels);
                if (flag) {
                    res.put("status", "success");
                } else {
                    res.put("status", "failed");
                }
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            res.put("status", "failed");
        }
        return res;
    }
}
