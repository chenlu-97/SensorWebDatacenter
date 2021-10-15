package com.sensorweb.datacentercwicservice.controller;

import com.sensorweb.datacentercwicservice.dao.RecordMapper;
import com.sensorweb.datacentercwicservice.service.InsertCWICService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
public class GetCWICController {

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private InsertCWICService insertCWICService;

    @GetMapping(value = "getCWICData")
    public Map<String, String> getCWICDataByConditions(@RequestParam("catalog") String catalog, @RequestParam("datasetId") String datasetId,
                                       @RequestParam("spatialLowerCorner") String spatialLowerCorner, @RequestParam("spatialUpperCorner") String spatialUpperCorner,
                                       @RequestParam("temporalBegin") String temporalBegin, @RequestParam("temporalEnd") String temporalEnd,
                                       @RequestParam("startPosition") int startPosition, @RequestParam("maxRecords") int maxRecords) {
        Map<String, String> res = new HashMap<>();
        try {
            boolean flag = insertCWICService.insertData(catalog, datasetId, spatialLowerCorner, spatialUpperCorner, temporalBegin, temporalEnd,
                    startPosition, maxRecords);
            if (flag) {
                res.put("status", "success");
            } else {
                res.put("status", "failed");
            }
        } catch (Exception e) {
            res.put("status", "failed");
        }
        return res;
    }

    @GetMapping("getCWICDataById")
    public Map<String, Object> getCWICDataById(@RequestParam("id") int id) {
        Map<String, Object> res = new HashMap<>();
        res.put("result", recordMapper.selectById(id));
        return res;
    }

}
