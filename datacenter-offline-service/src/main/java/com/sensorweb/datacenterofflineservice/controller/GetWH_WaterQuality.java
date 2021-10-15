package com.sensorweb.datacenterofflineservice.controller;


import com.sensorweb.datacenterofflineservice.dao.WH_WaterQualityMapper;
import com.sensorweb.datacenterofflineservice.entity.WH_WaterQuality;
import com.sensorweb.datacenterofflineservice.entity.WaterPollution;
import com.sensorweb.datacenterofflineservice.service.GetWH_WaterQualityService;
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
public class GetWH_WaterQuality {

    @Autowired
    GetWH_WaterQualityService getWH_WaterQualityService;

    @Autowired
    WH_WaterQualityMapper wh_WaterQualityMapper;




    @ApiOperation("分页查询水质量数据")
    @GetMapping(path = "getWaterQualityByPage")
    public Map<String, Object> getWaterQualityByPage(@ApiParam(name = "pageNum", value = "当前页码") @Param("pageNum") int pageNum,
                                                                     @ApiParam(name = "pageSize", value = "每页的数据条目数") @Param("pageSize") int pageSize) {
        Map<String, Object> res = new HashMap<>();
        List<WH_WaterQuality> info =  wh_WaterQualityMapper.selectByPage(pageNum, pageSize);
        int count=wh_WaterQualityMapper.selectNum1();
        Object num = new Integer(count);
        res.put("Info", info);
        res.put("num",num);
        return res;

    }


    @ApiOperation("根据id的分页查询水质量数据")
    @GetMapping(path = "getWaterQualityByID")
    @ResponseBody
    public Map<String, List<WH_WaterQuality>> getWaterQualityByID(@RequestParam(value = "uniquecode") List<String>  uniquecode, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize) {
        Map<String, List<WH_WaterQuality>> res = new HashMap<>();
        List<WH_WaterQuality> info = wh_WaterQualityMapper.selectByIds(uniquecode,pageNum,pageSize);
        res.put("Info", info);
        return res;
    }


    @ApiOperation("分页查询水质量(自动站)数据")
    @GetMapping(path = "getWaterQualityAutoByPage")
    public Map<String, Object> getWaterQualityAutoByPage(@ApiParam(name = "pageNum", value = "当前页码") @Param("pageNum") int pageNum,
                                                                    @ApiParam(name = "pageSize", value = "每页的数据条目数") @Param("pageSize") int pageSize) {
        Map<String, Object> res = new HashMap<>();
        List<WH_WaterQuality> info =  wh_WaterQualityMapper.selectAutoByPage(pageNum, pageSize);
        int count=wh_WaterQualityMapper.selectNum2();
        Object num = new Integer(count);
        res.put("Info", info);
        res.put("num",num);
        return res;
    }


    @ApiOperation("根据id的分页查询水质量（自动站）数据")
    @GetMapping(path = "getWaterQualityAutoByID")
    @ResponseBody
    public Map<String, List<WH_WaterQuality>> getWaterQualityAutoByID(@RequestParam(value = "uniquecode") List<String>  uniquecode, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize) {
        Map<String, List<WH_WaterQuality>> res = new HashMap<>();
        List<WH_WaterQuality> info = wh_WaterQualityMapper.selectAutoByIds(uniquecode,pageNum,pageSize);
        res.put("Info", info);
        return res;
    }





    @PostMapping(value = "registryWHWaterQuality1")
    public Map<String, String> registryWHWaterQuality1(@RequestParam("file") MultipartFile file) {
        String tmpDir = "/data/Ai-Sensing/DataCenter/water/water_data/";
//        String tmpDir = "C:/Users/chenlu/Desktop/省站资料/省站资料（修改）/water_data/月均数据/";
        Map<String, String> res = new HashMap<>();
        try {
            if (file!=null) {
                String fileName = file.getOriginalFilename();
                File excel = new File(tmpDir + fileName);
                file.transferTo(excel);
                List<WH_WaterQuality> WaterQuality =getWH_WaterQualityService.getQualityInfoByExcel(excel);
                boolean flag1 = getWH_WaterQualityService.insertQualityInfoBatch(WaterQuality);
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
        return res;
    }


    @PostMapping(value = "registryWHWaterQuality2")
    public Map<String, String> registryWHWaterQuality2(@ApiParam(name = "file", value = "文件数组") @RequestParam("file") MultipartFile[] files) {
        String tmpDir = "/data/Ai-Sensing/DataCenter/water/water_data/";
//        String tmpDir = "C:/Users/chenlu/Desktop/省站资料/省站资料（修改）/water_data/自动站数据/";
        Map<String, String> res = new HashMap<>();
        if (files != null && files.length > 0) {
            for (int i=0; i< files.length; i++) {
                MultipartFile file = files[i];
            try {
                if (file != null) {
                    String fileName = file.getOriginalFilename();
                    File excel = new File(tmpDir + fileName);
                    file.transferTo(excel);
                    List<WH_WaterQuality> WaterQuality = getWH_WaterQualityService.getQualityInfoByExcel2(excel);
                    boolean flag1 = false;
                    for (WH_WaterQuality s : WaterQuality) {
                        flag1 = getWH_WaterQualityService.insertWH_WaterQuality_AutoStation(s);
                    }
//                for(int i=0;i<WaterQuality.size();i++){
//                    boolean flag1 = getWH_WaterQualityService.insertWH_WaterQuality_AutoStation(WaterQuality.get(i));
//                }
                    if (flag1) {
                        res.put("status", "success");
                    } else {
                        res.put("status", "failed");
                    }
                }
            } catch (Exception e) {
                log.info(e.getMessage());
                System.out.println("excel出错的地方："+i);
                res.put("status", "failed");
            }
          }
        }
            return res;
        }



    @ApiOperation("查询数据总量")
    @GetMapping(path = "getWaterQualityNumber")
    public int getWaterQualityNum() {
        int count = getWH_WaterQualityService.getWHWaterQualityNum();
        return count;
    }

}
