package com.sensorweb.datacenterofflineservice.controller;



import com.sensorweb.datacenterofflineservice.dao.WaterPollutionMapper;
import com.sensorweb.datacenterofflineservice.entity.GF;
import com.sensorweb.datacenterofflineservice.entity.WaterPollution;
import com.sensorweb.datacenterofflineservice.service.GetWaterPollutionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
public class GetWaterPollution {

    @Autowired
    GetWaterPollutionService getWaterPollutionService;

    @Autowired
    WaterPollutionMapper waterPollutionMapper;




    @ApiOperation("分页查询水污染数据")
    @GetMapping(path = "getWaterPollutionByPage")
    public Map<String, Object> getWaterPollutionByPage(@ApiParam(name = "pageNum", value = "当前页码") @Param("pageNum") int pageNum,
                                                         @ApiParam(name = "pageSize", value = "每页的数据条目数") @Param("pageSize") int pageSize) {
        Map<String, Object> res = new HashMap<>();
        List<WaterPollution> info =  waterPollutionMapper.selectByPage(pageNum, pageSize);
        int count = getWaterPollutionService.getHBWaterPollutionNum();
        Object num = new Integer(count);
        res.put("Info", info);
        res.put("num",num);
        return res;
    }


    @ApiOperation("根据id的分页查询水污染数据")
    @GetMapping(path = "getWaterPollutionByID")
    @ResponseBody
    public Map<String, List<WaterPollution>> getWaterPollutionByID(@RequestParam(value = "uniquecode") List<String>  uniquecode, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize) {
        Map<String, List<WaterPollution>> res = new HashMap<>();
        List<WaterPollution> info = waterPollutionMapper.selectByIds(uniquecode,pageNum,pageSize);
        res.put("Info", info);
        return res;
    }



    @ApiOperation("查询数据总量")
    @GetMapping(path = "getWaterPollutionNumber")
    public int getWaterPollutionNum() {
        int count = getWaterPollutionService.getHBWaterPollutionNum();
        return count;
    }


    @PostMapping(value = "registryWaterPollution")
    public Map<String, String> registryWHWaterPollution(@ApiParam(name = "file", value = "文件数组") @RequestParam("file") MultipartFile[] files) {
        String tmpDir = "/data/Ai-Sensing/DataCenter/water/pollution_data/";
//        String tmpDir = "C:/Users/chenlu/Desktop/省站资料/省站资料（修改）/pollution_data/";
        Map<String, String> res = new HashMap<>();
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                try {
                    if (file != null) {
                        String fileName = file.getOriginalFilename();
                        File excel = new File(tmpDir + fileName);
                        file.transferTo(excel);
                        List<WaterPollution> waterPollution = getWaterPollutionService.getInfoByExcel(excel);
                        boolean flag1 = false;
                        for (WaterPollution s : waterPollution) {
                            flag1 = getWaterPollutionService.insertDataInfo(s);
                        }
                        if (flag1) {
                            res.put("status", "success");
                        } else {
                            res.put("status", "failed");
                        }
                    }
                } catch (Exception e) {
                    log.info(e.getMessage());
                    res.put("status", "failed");
                }
            }
        }
        return res;
    }


}
