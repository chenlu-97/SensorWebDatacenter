package com.sensorweb.datacentermobileservice.controller;


import com.sensorweb.datacentermobileservice.dao.SurveyingVesselMapper;
import com.sensorweb.datacentermobileservice.entity.SurveyingVessel;
import com.sensorweb.datacentermobileservice.service.SurveyingVesselService;
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
public class SurveyingVesselController {

    @Autowired
    SurveyingVesselService surveyingVesselService;

    @Autowired
    SurveyingVesselMapper surveyingVesselMapper;

    @ApiOperation("分页查询数据")
    @GetMapping(path = "get SurveyingVesselByPage")
    public Map<String, Object> getSurveyingVesselByPage(@ApiParam(name = "pageNum", value = "当前页码") @Param("pageNum") int pageNum,
                                                       @ApiParam(name = "pageSize", value = "每页的数据条目数") @Param("pageSize") int pageSize) {
        Map<String, Object> res = new HashMap<>();
        List<SurveyingVessel> info =  surveyingVesselService.getDataByPage(pageNum, pageSize);
        int count =surveyingVesselMapper.selectNum();
        Object num = new Integer(count);
        res.put("Info", info);
        res.put("num",num);
        return res;
    }
}
