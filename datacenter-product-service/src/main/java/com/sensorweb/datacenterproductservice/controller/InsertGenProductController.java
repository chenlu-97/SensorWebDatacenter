package com.sensorweb.datacenterproductservice.controller;

import com.sensorweb.datacenterproductservice.entity.GenProduct;
import com.sensorweb.datacenterproductservice.service.InsertGenProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class InsertGenProductController {

    @Autowired
    private InsertGenProductService insertGenProductService;

    @PostMapping(value = "registryGenProduct")
    @ResponseBody
    public Map<String, String> registryGenProduct(@RequestBody GenProduct genProduct) {
        Map<String, String> res = new HashMap<>();
        if (genProduct!=null) {
            boolean flag = insertGenProductService.insertGenProduct(genProduct);
            if (flag) {
                res.put("status", "success");
            } else {
                res.put("status", "failed");
            }
        }
        return res;
    }





}
