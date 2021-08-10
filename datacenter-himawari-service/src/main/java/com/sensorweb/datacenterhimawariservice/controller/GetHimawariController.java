package com.sensorweb.datacenterhimawariservice.controller;

import com.sensorweb.datacenterhimawariservice.dao.HimawariMapper;
import com.sensorweb.datacenterhimawariservice.entity.Himawari;
import com.sensorweb.datacenterhimawariservice.service.InsertHimawariService;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RestController
@CrossOrigin
public class GetHimawariController {

    @Autowired
    private HimawariMapper himawariMapper;

    @Autowired
    private InsertHimawariService insertHimawariService;

    /**
     * 获取数据
     * @param datetime yyyy-MM-ddTHH:mm:ss
     * @return
     */
    @GetMapping(value = "getHimawariData")
    public Map<String, String> getHimawariData(@RequestParam("datetime") String datetime) {

        Map<String, String> res = new HashMap<>();
        try {
            LocalDateTime time = DataCenterUtils.string2LocalDateTime(datetime);
            boolean flag = insertHimawariService.insertData(time);
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

    @GetMapping("getHimawariDataById")
    public String getHimawariDataById(@RequestParam("time") Instant time) {
        List<Himawari> res = himawariMapper.selectByTime(time);
        String path = null;
        if(res!=null && res.size()>0){
            path = res.get(0).getLocalPath();
        }
        return path;
    }


    @GetMapping("getHimawariMaxTimeData")
    public String getHimawariMaxTimeData() {
        List<Himawari> res = himawariMapper.selectMaxTimeData();
        String path = null;
        if(res!=null && res.size()>0){
            path = res.get(0).getLocalPath();
        }
        return path;
    }


    @ApiOperation("查询数据总量")
    @GetMapping(path = "getHimawariNumber")
    public int getHimawariNum() {
        int count = himawariMapper.selectNum();
        return count;
    }



    @ApiOperation("分页查询Himawari数据")
    @GetMapping(path = "getHimawariByPage")
    public Map<String, Object> getHimawariByPage(@ApiParam(name = "pageNum", value = "当前页码") @Param("pageNum") int pageNum,
                                                         @ApiParam(name = "pageSize", value = "每页的数据条目数") @Param("pageSize") int pageSize) {
        Map<String, Object> res = new HashMap<>();
        List<Himawari> info =  himawariMapper.selectByPage(pageNum, pageSize);
        int count = himawariMapper.selectNum();
        Object num = new Integer(count);
        res.put("Info", info);
        res.put("num",num);
        return res;
    }


    @ApiOperation("根据id的分页查询Himawari数据")
    @GetMapping(path = "getHimawariByID")
    @ResponseBody
    public Map<String, List<Himawari>>getHimawariByID(@RequestParam(value = "uniquecode") List<String>  uniquecode, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize) {
        Map<String, List<Himawari>> res = new HashMap<>();
        List<Himawari> info =  himawariMapper.selectByIds(uniquecode,pageNum,pageSize);
        res.put("Info", info);
        return res;
    }


    @ApiOperation("查询影像数据的空间范围")
    @GetMapping(value = "getHimawariCoordinates")
    @ResponseBody
    public List<Map> getHimawariCoordinates() {
        List<Map> res = new ArrayList<>();
        List<Himawari> info =  himawariMapper.selectMaxTimeData();
        if(info.size()>0&& info !=null) {
            for (Himawari himawari : info) {
                Map map = new LinkedHashMap();
                String id = himawari.getName();
                String str = "75,1,75,55,150,55,150,1,75,1";
                String[] tmp = str.split(",");
                List<Integer> point = new ArrayList<>();
                for(String num :tmp){
                    point.add(Integer.valueOf(num));
                }
                map.put("id",id);
                map.put("coordinate",point);
                res.add(map);
            }
        }
        return res;
    }


}
