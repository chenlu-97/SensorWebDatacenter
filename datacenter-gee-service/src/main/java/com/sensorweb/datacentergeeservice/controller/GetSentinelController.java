package com.sensorweb.datacentergeeservice.controller;

import com.sensorweb.datacentergeeservice.dao.LandsatMapper;
import com.sensorweb.datacentergeeservice.dao.SentinelMapper;
import com.sensorweb.datacentergeeservice.entity.Landsat;
import com.sensorweb.datacentergeeservice.entity.Sentinel;
import com.sensorweb.datacentergeeservice.service.LandsatService;
import com.sensorweb.datacentergeeservice.service.SentinelService;
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



@Slf4j
@RestController
@CrossOrigin
public class GetSentinelController {


    @Autowired
    SentinelService sentinelService;

    @Autowired
    SentinelMapper sentinelMapper;



    @ApiOperation("分页查询数据")
    @GetMapping(path = "getSentinelByPage")
    public Map<String, Object> getSentinelByPage(@ApiParam(name = "pageNum", value = "当前页码") @Param("pageNum") int pageNum,
                                                @ApiParam(name = "pageSize", value = "每页的数据条目数") @Param("pageSize") int pageSize) {
        Map<String, Object> res = new HashMap<>();
        List<Sentinel> info =  sentinelService.getSentinelByPage(pageNum, pageSize);
        int count =  sentinelService.getSentinelNum();
        Object num = new Integer(count);
        res.put("Info", info);
        res.put("num",num);
        return res;

    }
}
