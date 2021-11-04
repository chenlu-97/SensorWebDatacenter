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
    @GetMapping(path = "getMeasuringVehicleByPage")
    public Map<String, Object> getAirQualityHourByPage(@ApiParam(name = "pageNum", value = "当前页码") @Param("pageNum") int pageNum,
                                                       @ApiParam(name = "pageSize", value = "每页的数据条目数") @Param("pageSize") int pageSize) {
        Map<String, Object> res = new HashMap<>();
        List<MeasuringVehicle> info = measuringVehicleService.getDataByPage(pageNum, pageSize);
        int count =measuringVehicleMapper.selectNum();
        Object num = new Integer(count);
        res.put("Info", info);
        res.put("num",num);
        return res;
    }
}
