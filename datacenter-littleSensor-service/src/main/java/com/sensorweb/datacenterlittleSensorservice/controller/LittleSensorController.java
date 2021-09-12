package com.sensorweb.datacenterlittleSensorservice.controller;

import com.sensorweb.datacenterlittleSensorservice.dao.LittleSensorMapper;
import com.sensorweb.datacenterlittleSensorservice.entity.LittleSensor;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
public class LittleSensorController {

    @Autowired
    private LittleSensorMapper littleSensorMapper;



    @ApiOperation("查询数据总量")
    @GetMapping(path = "getLittleSensorNumber")
    public int getLittleSensorNumber() {
        int count = littleSensorMapper.selectNum();
        return count;
    }



    @ApiOperation("分页查询littleSensor数据")
    @GetMapping(path = "getLittleSensorByPage")
    public Map<String, Object> getLittleSensorByPage(@ApiParam(name = "pageNum", value = "当前页码") @Param("pageNum") int pageNum,
                                                          @ApiParam(name = "pageSize", value = "每页的数据条目数") @Param("pageSize") int pageSize) {
        Map<String, Object> res = new HashMap<>();
        List<LittleSensor> info = littleSensorMapper.selectByPage(pageNum, pageSize);
        int count = littleSensorMapper.selectNum();
        Object num = new Integer(count);
        res.put("Info", info);
        res.put("num",num);
        return res;

    }

    @ApiOperation("根据id的分页查询LittleSensor数据")
    @GetMapping(path = "getLittleSensorByID")
    @ResponseBody
    public Map<String, Object> getLittleSensorByID(@RequestParam(value = "uniquecode") List<String> uniquecode, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize) {
        Map<String, Object> res = new HashMap<>();

        List<LittleSensor> info =  littleSensorMapper.selectByIds(uniquecode,pageNum,pageSize);
        int sensor_size = uniquecode.size();
        int obs_size = info.size();

        Object num1 = new Integer(sensor_size);
        Object num2 = new Integer(obs_size);
        res.put("sensor_size", num1);
        res.put("obs_size", num2);
        res.put("Info", info);
        return res;
    }



    @ApiOperation("LittleSensor点的数量，站点数量")
    @GetMapping(path = "getLittleSensorPoint")
    @ResponseBody
    public Map<String, List<LittleSensor>> getLittleSensorPoint(@RequestParam(value = "uniquecode") List<String> uniquecode, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize) {
        Map<String, List<LittleSensor>> res = new HashMap<>();
        List<LittleSensor> info =  littleSensorMapper.selectByIds(uniquecode,pageNum,pageSize);
        res.put("Info", info);
        return res;
    }


}
