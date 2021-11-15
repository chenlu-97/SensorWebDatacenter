package com.sensorweb.sossensorservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import com.sensorweb.sossensorservice.entity.Platform;
import com.sensorweb.sossensorservice.service.DescribeSensorExpandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@RestController
@Api("传感器查询相关API")
@Slf4j
@CrossOrigin
public class GetSensorController {
    @Autowired
    private DescribeSensorExpandService describeSensorExpandService;



/**
 * 获取卫星的tle数据，转为CZML数据
 * 转发给前端cesium
 *
*/

    @ApiOperation("获取卫星轨迹  CZML格式返回")
    @GetMapping(path = "getCZML")
    public String getCZML() throws IOException {
        String url = "http://www.orbitalpredictor.com/api/predict_orbit";
        String ids="40267,39084,25994,27424,39150,40118,43484,44703,41882,40697,38038,49256";
        //Himawari8 40267 landsat8 39084  TERRA  25994  AQUA 27424  GAOFEN 1  39150  GAOFEN 2 40118  GAOFEN 6 43484  GAOFEN 7 44703, FY-4 40697  zy 38038 jilin-2 49256
        SimpleDateFormat sd = new SimpleDateFormat();// 格式化时间
        sd.applyPattern("yyyy-MM-dd_HH:mm");
        Date date = new Date();// 获取当前时间
        Date date2 = date;
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime();   //这个时间就是日期往后推一天的结果

        System.out.println("现在时间：" + sd.format(date2)); // 输出已经格式化的现在时间(24小时制)
        System.out.println("现在时间：" + sd.format(date));

        String start_time = sd.format(date);
        String end_time = sd.format(date2);
        String param = "sats="+ids+"&start="+start_time+"&end="+end_time+"&format=czml&type=orbit";
        String document = DataCenterUtils.doGet(url, param);
        if (document!=null) {
            return document;
        }
        return null;
    }



//    /**
//     * 获取系统中已注册传感器信息
//     */
//    @ApiOperation("获取所有的传感器信息,包括传感器和平台信息")
//    @GetMapping(path = "getAllProcedureInfo")
//    public List<Platform> getAllProcedure() {
//        //获取Header中的信息
//        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        assert servletRequestAttributes != null;
//        HttpServletRequest request = servletRequestAttributes.getRequest();
//        String ss = request.getHeader("user");
//        return describeSensorExpandService.getTOC();
//    }


    /**
     * 获取系统中已注册传感器信息
     */
    @ApiOperation("获取所有的传感器信息,包括传感器和平台信息")
    @GetMapping(path = "getAllProcedureInfo")
    public List<Platform> getAllProcedure() {
        //获取Header中的信息
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String ss = request.getHeader("user");
        return describeSensorExpandService.getTOC();
    }



    /**
     * 获取系统中已注册的传感器信息
     */
    @ApiOperation("根据平台id来获取所有的传感器信息,包括传感器和平台信息")
    @GetMapping(path = "getProcedureByPlatformId")
    public List<Platform> getProcedureByPlatformId(@RequestParam(value = "platformId") String platformId) {
        //获取Header中的信息
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String ss = request.getHeader("user");
        List<String> id = new ArrayList<>();
        if(platformId.equals("1")){
            id.add("urn:湖北省:def:identifier:OGC:2.0:环境监测站数据中心");
            id.add("urn:全国:def:identifier:OGC:2.0:全国空气质量网");
        }else if(platformId.equals("2")){
            id.add("urn:全国:def:identifier:OGC:2.0:全国气象网");
        }else if(platformId.equals("3")){
            id.add("urn:湖北省:def:identifier:OGC:2.0:长江流域水环境中心（水污染）");
            id.add("urn:湖北省:def:identifier:OGC:2.0:长江流域水环境中心（水质量）");
            id.add("urn:湖北省:def:identifier:OGC:2.0:长江流域水环境中心（自动站）");
        }else if(platformId.equals("4")) {
            id.add("urn:武汉大学:def:identifier:OGC:2.0:空气微小传感器");
        }
        List<Platform> Procedure= describeSensorExpandService.getPartOfTOC123(id);
        return Procedure;
    }


    /**
     * 查询procedure是否存在
     */
    @ApiOperation("判断传感器或平台是否存在")
    @GetMapping("isExist/{id}")
    public boolean isExist(@ApiParam(name = "procedureId", value = "传感器或平台的id") @PathVariable("id") String procedureId) {
        return describeSensorExpandService.isExist(procedureId);
    }

}
