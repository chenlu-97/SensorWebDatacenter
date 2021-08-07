package com.sensorweb.sossensorservice.controller;

import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import com.sensorweb.sossensorservice.service.*;
import com.sensorweb.sossensorservice.util.SensorConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.vast.ows.sos.InsertSensorRequest;
import java.util.HashMap;
import java.util.Map;

@Api("传感器注册相关API")
@Slf4j
@CrossOrigin
@RestController
public class InsertSensorController implements SensorConstant {

    @Autowired
    private InsertSensorService insertSensorService;

    /**
     * 批量注册传感器/procedure
     * @param files
     * @return
     * @throws Exception
     */
    @ApiOperation("注册传感器,通过xml文件注册")
    @PostMapping(path = "registryByFile")
    public Map<String, String> insertSensor(@ApiParam(name = "files", value = "文件数组") @RequestParam("files") MultipartFile[] files) {
        Map<String, String> res = new HashMap<>();
        if (files!=null && files.length>0) {
            for (int i=0; i< files.length; i++) {
                MultipartFile file = files[i];
                try {
                    String temp = DataCenterUtils.readFromMultipartFile(file);
                    String content = temp.substring(temp.indexOf("<sml"));
                    InsertSensorRequest request = insertSensorService.getInsertSensorRequest(SensorConstant.INSERT_SENSOR_PREFIX + content + SensorConstant.INSERT_SENSOR_SUFFIX);
                    insertSensorService.insertSensor(request);
                    res.put("status", file.getOriginalFilename() + ": success");
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info(e.getMessage());
                    res.put("status", file.getOriginalFilename() + ": failed");
                }

            }
        }
        return res;
    }

    /**
     * 通过XML文档注册传感器，不支持批量
     */
    @ApiOperation("注册传感器,通过xml文档内容注册")
    @PostMapping(path = "registryByXML")
    public Map<String, String> insertSensor(@ApiParam(name = "xmlContent", value = "xml文件内容") String xmlContent) {
        Map<String, String> res = new HashMap<>();
        try {
            if (!StringUtils.isBlank(xmlContent)) {
                String content = xmlContent.substring(xmlContent.indexOf("<sml"));
                InsertSensorRequest request = insertSensorService.getInsertSensorRequest(SensorConstant.INSERT_SENSOR_PREFIX + content + SensorConstant.INSERT_SENSOR_SUFFIX);
                insertSensorService.insertSensor(request);
                res.put("status", "success");
            } else {
                log.info("xmlContent is empty");
                res.put("status", "failed");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            res.put("status", "failed");
        }

        return res;
    }

}
