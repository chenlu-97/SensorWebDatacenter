package com.sensorweb.datacenterproductservice.controller;

import com.sensorweb.datacenterproductservice.entity.Product;
import com.sensorweb.datacenterproductservice.service.GetProductService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
@Api("数据产品查询相关API")
public class GetProductController {

    @Autowired
    private GetProductService getProductService;

    @GetMapping(value = "getByServiceAndTime")
    @ResponseBody
    public Map<String, List<Product>> getProductInfoByID(@RequestParam("serviceName") String serviceName, @RequestParam("manufactureDate") String time) {
        Map<String, List<Product>> res = new HashMap<>();
        List<Product> info =  getProductService.getproductByServiceAndTime(serviceName, time);
        res.put("ProductInfo", info);
        return res;
    }


    @GetMapping(value = "getproductByattribute")
    @ResponseBody
    public Map<String, List<Product>> getProductInfoByattribute(@RequestParam("productName") String productName, @RequestParam("timeResolution") String timeResolution, @RequestParam("spatialResolution") String spatialResolution, @RequestParam("dimension") String dimension) {
        Map<String, List<Product>> res = new HashMap<>();
        List<Product> info =  getProductService.getproductByattribute(productName,timeResolution,spatialResolution,dimension);
        res.put("ProductInfo", info);
        return res;

    }


    @GetMapping(value = "getAllproduct")
    @ResponseBody
    public Map<String, List<Product>> getAllProductInfo() {
        Map<String, List<Product>> res = new HashMap<>();
        List<Product> info =  getProductService.getAllproduct();
        res.put("ProductInfo", info);
        return res;
    }



}
