package com.sensorweb.datacenterlaadsservice.controller;

import com.sensorweb.datacenterlaadsservice.dao.EntryMapper;
import com.sensorweb.datacenterlaadsservice.entity.Entry;
import com.sensorweb.datacenterlaadsservice.service.InsertLAADSService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
public class LAADSController {

    @Autowired
    private EntryMapper entryMapper;

    @Autowired
    private InsertLAADSService insertLaadsService;

    @GetMapping(value = "getLaadsData")
    public Map<String, String> getLaadsData(String satellite, String product, String startTime, String endTime, String bbox) {

        Map<String, String> res = new HashMap<>();
        try {
            boolean flag = insertLaadsService.insertData(satellite, startTime, endTime, bbox, product);
            if (flag) {
                res.put("status", "success");
            } else {
                res.put("status", "failed");
            }
        } catch (Exception e) {
            res.put("status", "failed");
        }
        return res;
    }

    @GetMapping("getLAADSDataById")
    public Map<String, Object> getLAADSDataById(@RequestParam("id") int id) {
        Map<String, Object> res = new HashMap<>();
        res.put("result", entryMapper.selectById(id));
        return res;
    }


    @GetMapping("getLAADS")
    public int getLAADS() {
        List<Entry> entryList = new ArrayList<>();
        Entry entry = new Entry();
        entry.setSatellite("1");
        entry.setEntryId("12");
        entry.setBbox("12133");
        entry.setProductType("12133");
        entry.setFilePath("12133");
        entry.setUpdated("12133");
        entry.setSummary("1213");
        entry.setWkt("POLYGON((119.88921295665621 32.43809123148852,120.7378487376959 32.272775667771676,121.52392173553908 32.11392926097289,121.52713013170285 32.1250148794685,121.59554547834652 32.36691622530554,121.6659985358775 32.61475609245087,121.73618880488671 32.86067740609684,121.80761210153415 33.10986251355583,121.88699656332598 33.38531657669484,121.94369387763581 33.58111802621447,122.01792679103042 33.83646201756913,122.01790658055839 33.838230418830506,121.5224570788418 33.93729939581788,120.67528383639105 34.10151678294821,120.32548043822243 34.1674169945179,120.007483607093 34.22636121664424,119.89051945454977 33.78834710068756,119.77884510492372 33.36646510952478,119.66808149921208 32.94452360765576,119.55290998356116 32.50211881560613,119.55432986776898 32.5015650616325,119.88921295665621 32.43809123148852))");
        entryList.add(entry);
        entryList.add(entry);
        entryList.add(entry);
        entryList.add(entry);
        entryMapper.insertDataBatch(entryList);
        return 1;
    }



    @ApiOperation("查询数据总量")
    @GetMapping(path = "getModisNumber")
    public int getModisNum() {
        int count = entryMapper.selectNum();
        return count;
    }



    @ApiOperation("分页查询laads数据")
    @GetMapping(path = "getLaadsByPage")
    public Map<String, Object> getLaadsByPage(@ApiParam(name = "pageNum", value = "当前页码") @Param("pageNum") int pageNum,
                                                              @ApiParam(name = "pageSize", value = "每页的数据条目数") @Param("pageSize") int pageSize) {
        Map<String, Object> res = new HashMap<>();
        List<Entry> info = entryMapper.selectByPage(pageNum, pageSize);
        int count = entryMapper.selectNum();
        Object num = new Integer(count);
        res.put("Info", info);
        res.put("num",num);
        return res;

    }

    @ApiOperation("根据id的laads分页查询")
    @GetMapping(path = "getLaadsByID")
    @ResponseBody
    public Map<String, List<Entry>> getLaadsByID(@RequestParam(value = "uniquecode") List<String> uniquecode, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize) {
        Map<String, List<Entry>> res = new HashMap<>();
        List<Entry> info =  entryMapper.selectByIds(uniquecode,pageNum,pageSize);
        res.put("Info", info);
        return res;
    }



    @GetMapping(value = "getLaadsDataTest")
    public Map<String, String> getLaadsDataTest(String satellite, String product, String startTime, String endTime, String bbox) {

        Map<String, String> res = new HashMap<>();
        try {
            boolean flag = insertLaadsService.insertData2(satellite, startTime, endTime, bbox, product);
            if (flag) {
                res.put("status", "success");
            } else {
                res.put("status", "failed");
            }
        } catch (Exception e) {
            res.put("status", "failed");
        }
        return res;
    }

    @GetMapping(value = "getMerra2DataTest")
    public Map<String, String> getMerra2DataTest(String time) {

        Map<String, String> res = new HashMap<>();
        try {
            boolean flag = insertLaadsService.insertData3(time);
            if (flag) {
                res.put("status", "success");
            } else {
                res.put("status", "failed");
            }
        } catch (Exception e) {
            res.put("status", "failed");
        }
        return res;
    }




}
