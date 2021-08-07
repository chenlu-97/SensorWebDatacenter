package com.sensorweb.datacenterairservice.controller;

import com.sensorweb.datacenterairservice.dao.AirStationMapper;
import com.sensorweb.datacenterairservice.entity.AirStationModel;
import com.sensorweb.datacenterairservice.entity.ChinaAirQualityHour;
import com.sensorweb.datacenterairservice.entity.TWEPA;
import com.sensorweb.datacenterairservice.service.InsertAirService;
import com.sensorweb.datacenterairservice.service.InsertChinaAirService;
import com.sensorweb.datacenterairservice.service.InsertTWEPA;
import com.sensorweb.datacenterairservice.util.AirConstant;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin
@RestController
public class StationController implements AirConstant {

    @Autowired
    private InsertAirService insertAirService;

    @Autowired
    private InsertChinaAirService insertChinaAirService;

    @Autowired
    private InsertTWEPA insertTWEPA;
    @Autowired
    private AirStationMapper airStationMapper;


    @ApiOperation("通过类型和空间对station进行查询，返回station的id集合")
    @GetMapping(value="getStationByTypeAndSpa")
    public Map<String,List<String>> getStationByTypeAndSpa(@RequestParam(value = "type") String type,@RequestParam(value = "spa") String spa){
        Map<String,List<String>> res = new HashMap<>();
        if(type.equals("AIR")){
            String Type = "HB_AIR,CH_AIR,TW_EPA_AIR";
            String[] dataTypes = Type.split(",");
            for(String datatype:dataTypes) {
                List<String> ids = airStationMapper.selectStationByTypeAndSpa(datatype, spa);
                res.put(datatype,ids);
            }
        }else if(type.equals("WEATHER")){
            String datatype = "wh_1+8_weather";
            List<String> ids = airStationMapper.selectStationByTypeAndSpa(datatype, spa);
            res.put(datatype,ids);
        }
        return res;
    }

    @ApiOperation("通过类型和空间对station进行查询，返回station的id集合")
    @GetMapping(value="getStationByTypeAndSpaOfAllHBAir")
    public List<String> getStationByTypeAndSpaOfAllHBAir(@RequestParam(value = "type") String type,@RequestParam(value = "spa") String spa) {
        List<String> ids = airStationMapper.selectStationByTypeAndSpa(type, spa);
        return ids;
    }





    @ApiOperation("由于station原先没有geom的空间范围，重新获取点坐标后进行插入")
    @GetMapping(value="insertGeom")
    public Boolean insertGeom(){
        boolean res = false;
        List<AirStationModel> stations = airStationMapper.selectByAll();
        for(AirStationModel station:stations){
            String wkt = "POINT(" +station.getLon() + " " + station.getLat() + ")";
            res = airStationMapper.updateGeom(wkt, station.getLon() , station.getLat());
        }
        return res;
    }



    @GetMapping(value = "registryWHAirStation")
    public Map<String, String> registryWHAirStation() throws Exception {
        Map<String, String> res = new HashMap<>();
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai")).minusHours(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00:00").withZone(ZoneId.of("Asia/Shanghai"));
        String time = formatter.format(dateTime);
        String param = "UsrName=" + AirConstant.USER_NAME + "&passWord=" + AirConstant.PASSWORD +
                "&date=" + URLEncoder.encode(time, "utf-8");
        String document = DataCenterUtils.doGet(AirConstant.GET_LAST_HOURS_DATA, param);
        List<Object> objects = insertAirService.parseXmlDoc(document);
        System.out.println("objects = " + objects);
        boolean flag = insertAirService.insertWHAirStationInfo(objects);
//        try {
//            String param = "UsrName=" + AirConstant.USER_NAME + "&passWord=" + AirConstant.PASSWORD +
//                    "&date=" + URLEncoder.encode(time, "utf-8");
//            String document = DataCenterUtils.doGet(AirConstant.GET_LAST_HOURS_DATA, param);
//            List<Object> objects = insertAirService.parseXmlDoc(document);
//            System.out.println("objects = " + objects);
//            boolean flag = insertAirService.insertWHAirStationInfo(objects);
//            if (flag) {
//                res.put("status", "success");
//            } else  {
//                res.put("status", "failed");
//            }
//        } catch (Exception e) {
//            log.debug(e.getMessage());
//            res.put("status", "failed");
//        }
        return res;
    }

    @GetMapping(value = "registryCHAirStation")
    public Map<String, String> registryCHAirStation() {
        Map<String, String> res = new HashMap<>();
        try {
            String document = insertChinaAirService.getApiDocument();
            if (!StringUtils.isBlank(document)) {
                List<ChinaAirQualityHour> chinaAirQualityHours = insertChinaAirService.getChinaAQHInfo(document);
                boolean flag = insertChinaAirService.insertCHAirStationInfo(chinaAirQualityHours);
                if (flag) {
                    res.put("status", "success");
                } else  {
                    res.put("status", "failed");
                }
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            res.put("status", "failed");
        }
        return res;
    }

    @GetMapping(value = "registryTWAirStation")
    public Map<String, String> registryTWAirStation() {
        Map<String, String> res = new HashMap<>();
        try {
            String document = insertTWEPA.getDocumentByGZip(insertTWEPA.downloadFile());
            if (!StringUtils.isBlank(document)) {
                List<TWEPA> twepas = insertTWEPA.getEPAInfo(document);
                boolean flag = insertTWEPA.insertTWAirStationInfo(twepas);
                if (flag) {
                    res.put("status", "success");
                } else  {
                    res.put("status", "failed");
                }
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            res.put("status", "failed");
        }
        return res;
    }






}
