package com.sensorweb.datacenterairservice;

import com.sensorweb.datacenterairservice.dao.AirQualityHourMapper;
import com.sensorweb.datacenterairservice.dao.AirStationMapper;
import com.sensorweb.datacenterairservice.dao.ChinaAirQualityHourMapper;
import com.sensorweb.datacenterairservice.dao.TWEPAMapper;
import com.sensorweb.datacenterairservice.entity.AirQualityHour;
import com.sensorweb.datacenterairservice.entity.AirStationModel;
import com.sensorweb.datacenterairservice.entity.ChinaAirQualityHour;
import com.sensorweb.datacenterairservice.entity.TWEPA;
import com.sensorweb.datacenterairservice.feign.ObsFeignClient;
import com.sensorweb.datacenterairservice.service.InsertChinaAirService;
import com.sensorweb.datacenterairservice.service.InsertTWEPA;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
class DatacenterInsertAirServiceApplicationTests {
    @Autowired
    private ObsFeignClient obsFeignClient;

    @Autowired
    private ChinaAirQualityHourMapper chinaAirQualityHourMapper;
    @Autowired
    private AirQualityHourMapper airQualityHourMapper;
    @Autowired
    private AirStationMapper airStationMapper;
    @Test
    void contextLoads() throws Exception {
//        String bbox = "180 0,180 0";
//        boolean isWH = obsFeignClient.insertBeforeSelectWHSpa(bbox);
//        boolean isCJ = obsFeignClient.insertBeforeSelectCJSpa(bbox);
//        System.out.println(isCJ);
//        System.out.println(isWH);
//        AirQualityHour airQualityHour = new AirQualityHour();
//        airQualityHour.setUniqueCode("123");
//        int status = airQualityHourMapper.insertData(airQualityHour);
//        System.out.println(status);

//        List<AirStationModel> stations = airStationMapper.selectByAll();
//        for(AirStationModel station:stations){
//            String wkt = "POINT(" +station.getLon() + " " + station.getLat() + ")";
//            System.out.println(wkt);
//            boolean res = airStationMapper.updateGeom(wkt,station.getLon(),station.getLat());
//            System.out.println(res);
//        }
        DataCenterUtils.sendMessage("HB_AIR", "省站湖北空气质量","这是一条省站推送的湖北省空气质量数据");
    }


}
