package com.sensorweb.datacentermobileservice.controller;


import com.sensorweb.datacentermobileservice.dao.MeasuringVehicleMapper;
import com.sensorweb.datacentermobileservice.entity.MeasuringVehicle;
import com.sensorweb.datacentermobileservice.service.MeasuringVehicleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@CrossOrigin
public class MeasuringVehicleController {

    @Autowired
    MeasuringVehicleService  measuringVehicleService;

    @Autowired
    MeasuringVehicleMapper  measuringVehicleMapper;

    @ApiOperation("分页查询数据")
    @GetMapping(path = "getVocsByPage")
    public Map<String, Object> getVocsByPage(@ApiParam(name = "pageNum", value = "当前页码") @Param("pageNum") int pageNum,
                                                       @ApiParam(name = "pageSize", value = "每页的数据条目数") @Param("pageSize") int pageSize) {
        Map<String, Object> res = new HashMap<>();
        List<MeasuringVehicle> info = measuringVehicleService.getVocsByPage(pageNum, pageSize);
        res.put("Info", info);
        return res;
    }
    @ApiOperation("分页查询数据")
    @GetMapping(path = "getHTByPage")
    public Map<String, Object> getHTByPage(@ApiParam(name = "pageNum", value = "当前页码") @Param("pageNum") int pageNum,
                                                         @ApiParam(name = "pageSize", value = "每页的数据条目数") @Param("pageSize") int pageSize) {
        Map<String, Object> res = new HashMap<>();
        List<MeasuringVehicle> info = measuringVehicleService.getHTByPage(pageNum, pageSize);
        res.put("Info", info);
        return res;
    }
    @ApiOperation("分页查询数据")
    @GetMapping(path = "getPMByPage")
    public Map<String, Object> getPMByPage(@ApiParam(name = "pageNum", value = "当前页码") @Param("pageNum") int pageNum,
                                                         @ApiParam(name = "pageSize", value = "每页的数据条目数") @Param("pageSize") int pageSize) {
        Map<String, Object> res = new HashMap<>();
        List<MeasuringVehicle> info = measuringVehicleService.getPMByPage(pageNum, pageSize);
        res.put("Info", info);
        return res;
    }
    @ApiOperation("分页查询数据")
    @GetMapping(path = "getAirByPage")
    public Map<String, Object> getAirByPage(@ApiParam(name = "pageNum", value = "当前页码") @Param("pageNum") int pageNum,
                                                         @ApiParam(name = "pageSize", value = "每页的数据条目数") @Param("pageSize") int pageSize) {
        Map<String, Object> res = new HashMap<>();
        List<MeasuringVehicle> info = measuringVehicleService.getAirByPage(pageNum, pageSize);
        res.put("Info", info);
        return res;
    }

    @ApiOperation("分页查询数据")
    @GetMapping(path = "getSPMSByPage")
    public Map<String, Object> getSPMSByPage(@ApiParam(name = "pageNum", value = "当前页码") @Param("pageNum") int pageNum,
                                                         @ApiParam(name = "pageSize", value = "每页的数据条目数") @Param("pageSize") int pageSize) {
        Map<String, Object> res = new HashMap<>();
        List<MeasuringVehicle> info = measuringVehicleService.getSPMSByPage(pageNum, pageSize);
        res.put("Info", info);
        return res;
    }
}
