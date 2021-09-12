package com.sensorweb.datacenterweatherservice.controller;

import com.sensorweb.datacenterweatherservice.dao.TianXingZhouMapper;
import com.sensorweb.datacenterweatherservice.entity.ChinaWeather;
import com.sensorweb.datacenterweatherservice.entity.TianXingZhouStation;
import com.sensorweb.datacenterweatherservice.service.TianXingZhouService;
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
public class TianXingZhouController {


    @Autowired
    TianXingZhouService tianXingZhouService;

    @Autowired
    TianXingZhouMapper tianXingZhouMapper;

    @ApiOperation("分页查询数据")
    @GetMapping(path = "getTianXingZhouByPage")
    public Map<String, Object> getAirQualityHourByPage(@ApiParam(name = "pageNum", value = "当前页码") @Param("pageNum") int pageNum,
                                                       @ApiParam(name = "pageSize", value = "每页的数据条目数") @Param("pageSize") int pageSize) {
        Map<String, Object> res = new HashMap<>();
        List<TianXingZhouStation> info =  tianXingZhouService.getDataByPage(pageNum, pageSize);
        int count =tianXingZhouMapper.selectNum();
        Object num = new Integer(count);
        res.put("Info", info);
        res.put("num",num);
        return res;
    }


}
