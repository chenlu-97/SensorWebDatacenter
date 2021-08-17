package com.sensorweb.datacenterweatherservice.controller;

import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import com.sensorweb.datacenterweatherservice.dao.WeatherMapper;
import com.sensorweb.datacenterweatherservice.entity.ChinaWeather;
import com.sensorweb.datacenterweatherservice.service.GetWeatherInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@Slf4j
@CrossOrigin
public class WeatherController {

    @Autowired
    private WeatherMapper weatherMapper;

    @Autowired
    private GetWeatherInfo getWeatherInfo;


    @ApiOperation("查询气象数据数据总量")
    @GetMapping(path = "getWeatherNumber")
    public int getWeatherNum() {
        int count = weatherMapper.selectNum();
        return count;
    }


    @GetMapping("getAllWeather")
    public Map<String, List<ChinaWeather>> getWeather() {
        Map<String, List<ChinaWeather>> res = new HashMap<>();
        res.put("info", weatherMapper.selectAll());
        return res;
    }


    @ApiOperation("查询24小时的数据接入数量")
    @GetMapping(path = "getWeatherCountOfHour")
    @ResponseBody
    public Integer getWeatherCountOfHour(@RequestParam(value = "start") Instant start) {
        int count =weatherMapper.selectByTime(start, start.plusSeconds(60*60));
        return count;
    }


    @ApiOperation("分页查询数据")
    @GetMapping(path = "getWeatherByPage")
    public Map<String, Object> getAirQualityHourByPage(@ApiParam(name = "pageNum", value = "当前页码") @Param("pageNum") int pageNum,
                                                                     @ApiParam(name = "pageSize", value = "每页的数据条目数") @Param("pageSize") int pageSize) {
        Map<String, Object> res = new HashMap<>();
        List<ChinaWeather> info =  getWeatherInfo.getWeatherByPage(pageNum, pageSize);
        int count = weatherMapper.selectNum();
        Object num = new Integer(count);
        res.put("Info", info);
        res.put("num",num);
        return res;
    }

    @ApiOperation("根据id的分页查询数据")
    @GetMapping(path = "getWeatherByID")
    @ResponseBody
    public Map<String, List<ChinaWeather>> getWeatherByID(@RequestParam(value = "uniquecode") List<String>  uniquecode, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize) {
        Map<String, List<ChinaWeather>> res = new HashMap<>();
        List<ChinaWeather> info =  weatherMapper.selectByIds(uniquecode,pageNum,pageSize);
        res.put("Info", info);
        return res;
    }



    @GetMapping("getWeatherById")
    public Map<String, Object> getWeatherById(@RequestParam("id") int id) {
        Map<String, Object> res = new HashMap<>();
        res.put("result", weatherMapper.selectById(id));
        return res;
    }

    @GetMapping(path = "getExportWeatherDataByIds")
    public Map<String, String> getExportWeatherDataIds(@RequestParam(value = "ids") List<String> ids, @RequestParam(value = "time") Instant time , @RequestParam(value = "geotype") String  geotype) throws ParseException {
        Map<String, String> res = new HashMap<>();
        List<ChinaWeather> chinaWeathers = weatherMapper.selectByIdsAndTime("wh_1+8_weather",geotype,time);
        String filePath = null;
                if(chinaWeathers !=null && chinaWeathers.size()>0) {
                    String out_time = chinaWeathers.get(0).getQueryTime().toString();
                    String pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
                    dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
                    LocalDateTime localTime = LocalDateTime.parse(out_time, dateTimeFormatter).plusSeconds(8*60*60);
                    String filename = "WEATHER" +"_"+geotype+"_" + replace(localTime.toString()+"00");
                    filePath = getWeatherInfo.exportTXT(chinaWeathers,filename);
                }
        res.put("filePath", filePath);
        return res;
    }

    public String replace (String out){
        String out1 =out.replace("-","");
        String out2 = out1.replace("T","");
        String out3 = out2.replace("Z","");
        String out4 = out3.replace(":","");
        return  out4;
    }
}
