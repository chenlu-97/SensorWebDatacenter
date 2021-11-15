package com.sensorweb.datacentergeeservice.controller;

import com.sensorweb.datacentergeeservice.dao.LandsatMapper;
import com.sensorweb.datacentergeeservice.entity.Landsat;
import com.sensorweb.datacentergeeservice.service.LandsatService;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@RestController
@CrossOrigin
public class GetLandsatController {
    @Autowired
    LandsatService landsatService;

    @Autowired
    LandsatMapper landsatMapper;


    @ApiOperation("分页查询数据")
    @GetMapping(path = "getLandsatByPage")
    public Map<String, Object> getLandsatByPage(@ApiParam(name = "pageNum", value = "当前页码") @Param("pageNum") int pageNum,
                                                       @ApiParam(name = "pageSize", value = "每页的数据条目数") @Param("pageSize") int pageSize) {
        Map<String, Object> res = new HashMap<>();
        List<Landsat> info =  landsatService.getLandsatByPage(pageNum, pageSize);
        int count = landsatService.getLandsatNum();
        Object num = new Integer(count);
        res.put("Info", info);
        res.put("num",num);
        return res;

    }


    @ApiOperation("根据id的分页查询landsat数据")
    @GetMapping(path = "getLandsatByID")
    @ResponseBody
    public Map<String, List<Landsat>> getLandsatByID(@RequestParam(value = "imageid") List<String> imageid, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize) {
        Map<String, List<Landsat>> res = new HashMap<>();
        List<Landsat> info =  landsatMapper.selectByIds(imageid,pageNum,pageSize);
        res.put("Info", info);
        return res;
    }


    @ApiOperation("获取landsat数据")
    @GetMapping(path = "getLandsat8")
    @ResponseBody
    public void getLandsat() {
        landsatService.getLandsat();
    }

    @ApiOperation("发送获取到的信息给前端")
    @GetMapping(path = "sendLandsat")
    @ResponseBody
    public void sendLandsat() throws Exception {
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Shanghai"));
        String time = formatter.format(dateTime);
        DataCenterUtils.sendMessage("Landsat-8"+ time, "Landsat-8","GEE获取的Landsat-8影像成功");
    }




    @GetMapping(value = "getLandsatByattribute")
    @ResponseBody
    public Map<String, List<Landsat>> getInfoByattribute(@Param("spacecraftID") String spacecraftID, @Param("Date") String Date, @Param("Cloudcover") String Cloudcover, @Param("imageType") String imageType) {
        Map<String, List<Landsat>> res = new HashMap<>();
        List<Landsat> info =  landsatService.getByattribute(spacecraftID,Date,Cloudcover,imageType);
        res.put("Info", info);
        return res;

    }


    @GetMapping(value = "getLandsat")
    @ResponseBody
    public Map<String, List<Landsat>> getAllInfo() {
        Map<String, List<Landsat>> res = new HashMap<>();
        List<Landsat> info =  landsatService.getAll();
        res.put("Info", info);
        return res;
    }

    @ApiOperation("查询影像数据的空间范围")
    @GetMapping(value = "getLandsatCoordinates")
    @ResponseBody
    public List<Map> getLandsatCoordinates() {
        List<Map> res = new ArrayList<>();
        List<Landsat> info =  landsatService.getAll();
        if(info.size()>0 && info !=null) {
            for (Landsat landsat : info) {
                Map map = new LinkedHashMap();
                String id = landsat.getImageID();
                String str = landsat.getCoordinates();
                String time = landsat.getDate();
                String coordinate = str.replace("POLYGON", "").replace("((","").replace("))","").replace(" ",",");
                String[] tmp = coordinate.split(",");
                List<Float> point = new ArrayList<>();
                for(String num :tmp){
                    point.add(Float.valueOf(num));
                }
                map.put("id",id);
                map.put("coordinate",point);
                map.put("time",time);
                res.add(map);
            }
        }
        return res;
    }



    @ApiOperation("查询数据总量")
    @GetMapping(path = "getLandsatNumber")
    public int getLandsatNum() {
        int count = landsatService.getLandsatNum();
        return count;
    }


}
