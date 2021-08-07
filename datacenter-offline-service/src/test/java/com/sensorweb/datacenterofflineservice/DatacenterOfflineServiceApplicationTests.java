package com.sensorweb.datacenterofflineservice;

import com.sensorweb.datacenterofflineservice.entity.WH_WaterQuality;
import com.sensorweb.datacenterofflineservice.entity.WH_WaterStation;
import com.sensorweb.datacenterofflineservice.entity.WaterPollution;
import com.sensorweb.datacenterofflineservice.entity.WaterPollutionStation;
import com.sensorweb.datacenterofflineservice.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DatacenterOfflineServiceApplicationTests {
//    @Autowired
//    GetGFService getGFService;
//    @Autowired
//    GetWFService getWeatherForecastService;
//    @Autowired
//    GetWH_WaterStationService getWH_WaterStationService;
//
//    @Autowired
//    GetWH_WaterQualityService getWH_WaterQualityService;

    @Autowired
    GetWaterPollutionService getWaterPollutionService;
    @Autowired
    GetWaterPollutionStationService getWaterPollutionStationService;


    @Test
    void contextLoads() throws Exception {
//        int status = getGFService.insertGF();
//        System.out.println(status);

//        getWeatherForecastService.insertWeatherForecast();

//        List<WH_WaterStation> WaterStation = new ArrayList<>();
//        WH_WaterStation wh_WaterStation = new WH_WaterStation();
//        wh_WaterStation.setRiverName("1234");
//        wh_WaterStation.setSectionName("1234");
//        wh_WaterStation.setStationId("1234");
//        wh_WaterStation.setLon(12.24);
//        wh_WaterStation.setLat(43.12);
//        wh_WaterStation.setGeom("POINT(" + 12.24 + ' ' + 43.12 + ")");
//        wh_WaterStation.setStationType("wh_1+8_Water");
//        WaterStation.add(wh_WaterStation);
//        boolean flag1 =  getWH_WaterStationService.insertStationInfoBatch(WaterStation);
//        boolean flag2 = getWH_WaterStationService.insertStationInfoBatchInStation(WaterStation);
//        System.out.println(flag1);
//        System.out.println(flag2);

//        List<WH_WaterQuality> WaterQuality = new ArrayList<>();
//        WH_WaterQuality wh_WaterQuality = new WH_WaterQuality();
//        wh_WaterQuality.setRiverName("1234");
//        wh_WaterQuality.setSectionName("1234");
//        WaterQuality.add(wh_WaterQuality);
//        boolean flag1 = getWH_WaterQualityService.insertDataBatch_AutoStation(WaterQuality);
//        System.out.println(flag1);
//
//
//            List<WH_WaterQuality> WaterQuality = new ArrayList<>();
//            WH_WaterQuality wh_WaterQuality = new WH_WaterQuality();
//            wh_WaterQuality.setRiverName("1234");
//            wh_WaterQuality.setSectionName("1234");
//            WaterQuality.add(wh_WaterQuality);
//            boolean flag1 = getWH_WaterQualityService.insertDataBatch_AutoStation(WaterQuality);
//            System.out.println(flag1);


//        List<WaterPollution> waterPollutions = new ArrayList<>();
//        WaterPollution waterPollution = new WaterPollution();
//        waterPollution.setStationName("1234");
//        waterPollution.setCompanyName("1234");
//        waterPollutions.add(waterPollution);
//        boolean flag1 = getWaterPollutionService.insertDataInfo(waterPollution);
//        System.out.println(flag1);


//        List<WaterPollutionStation> waterPollutions = new ArrayList<>();
//        WaterPollutionStation waterPollution = new WaterPollutionStation();
//        waterPollution.setStationName("1234");
//        waterPollution.setStationType("1234");
//        waterPollutions.add(waterPollution);
//        boolean flag1 = getWaterPollutionStationService.insertStationInfoBatch(waterPollutions);
//        boolean flag2 = getWaterPollutionStationService.insertStationInfoBatchInStation(waterPollutions);
//        System.out.println(flag1);
//        System.out.println(flag2);
    }
}
