package com.sensorweb.datacenterproductservice.controller;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sensorweb.datacenterproductservice.entity.Product;
import com.sensorweb.datacenterproductservice.service.InsertProductService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin

@Api("数据产品查询相关API")
public class InsertProductController {
    @Value("${datacenter.path.product}")
    private String targetDir;

    @Autowired
    private InsertProductService insertProductService;


//    @PostMapping(value = "insertProduct")
//    public void insertProduct(@RequestBody Product info) {
//       insertProductService.InsertProduct(info);
//    }


    @GetMapping(value = "registryProduct")
    public Map<String, String> registryProduct(@RequestParam("tmpDir") String tmpDir) {
        Map<String, String> res = new HashMap<>();
        insertProductService.moveFileByPath(tmpDir, targetDir);
        res.put("status", "success");
        return res;
    }


    @PostMapping(value = "insertProduct")
    @ResponseBody
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Map<String, String> insertProduct(@RequestBody Product product) {
        Map<String, String> res = new HashMap<>();
        if (product!=null) {
            boolean flag = insertProductService.insertAirPollutionPrediction(product);
            if (flag) {
                res.put("status", "success");
            } else {
                res.put("status", "failed");
            }
        }
        return res;
    }

}
