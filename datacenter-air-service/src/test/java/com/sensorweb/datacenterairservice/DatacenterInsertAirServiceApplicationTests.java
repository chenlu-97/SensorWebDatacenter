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
import com.sensorweb.datacenterairservice.service.InsertAirService;
import com.sensorweb.datacenterairservice.service.InsertChinaAirService;
import com.sensorweb.datacenterairservice.service.InsertTWEPA;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import com.sensorweb.datacenterutil.utils.OkHttpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class DatacenterInsertAirServiceApplicationTests {
//    @Autowired
//    private ObsFeignClient obsFeignClient;
//
//    @Autowired
//    private ChinaAirQualityHourMapper chinaAirQualityHourMapper;
//    @Autowired
//    private AirQualityHourMapper airQualityHourMapper;
//    @Autowired
//    private AirStationMapper airStationMapper;

    @Autowired
    private InsertAirService insertAirService;
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
            DataCenterUtils.sendMessage("CH_AIR", "全国空气质量","这是一条获取的全国空气质量数据");

//        LocalDateTime begin_date = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
//        LocalDateTime end_date = begin_date.plusSeconds(24*60*60);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.of("Asia/Shanghai"));
//        String begin = formatter.format(begin_date);
//        String end = formatter.format(end_date);
//        System.out.println("begin = " + begin);
//        System.out.println("end = " + end);
    }


}
